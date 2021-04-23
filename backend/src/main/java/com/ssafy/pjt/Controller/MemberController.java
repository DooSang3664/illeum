package com.ssafy.pjt.Controller;

import com.ssafy.pjt.Repository.MemberRepository;
import com.ssafy.pjt.dto.Member;
import com.ssafy.pjt.dto.Token;
import com.ssafy.pjt.dto.request.LoginDto;
import com.ssafy.pjt.dto.request.SignUpDto;
import com.ssafy.pjt.dto.request.UpdateMemberDto;
import com.ssafy.pjt.jwt.JwtTokenUtil;
import com.ssafy.pjt.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@RestController
@CrossOrigin
@RequestMapping("/api/member") // This means URL's start with /demo (after Application path)
public class MemberController {
    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
    @Autowired
    private MemberRepository accountRepository;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager am;
    @Autowired
    private PasswordEncoder bcryptEncoder;

    @ApiOperation(value = "로그인")
    @PostMapping(path = "/user/login")
    public ResponseEntity<?> login(@RequestBody LoginDto login) throws Exception {
        final String email = login.getEmail();
        logger.info("test input username: " + email);
        try {
            am.authenticate(new UsernamePasswordAuthenticationToken(email, login.getPassword()));
        } catch (Exception e){
        	return new ResponseEntity<>("fail",HttpStatus.NO_CONTENT);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(email);

        Token retok = new Token();
        retok.setUsername(email);
        retok.setRefreshToken(refreshToken);

        //generate Token and save in redis
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(email, retok);

        logger.info("generated access token: " + accessToken);
        logger.info("generated refresh token: " + refreshToken);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }
    
    @ApiOperation(value = "로그아웃")
    @PostMapping(path="/user/logout")
    public ResponseEntity<?> logout(@RequestBody String accessToken) {
        String username = null;
        try {
        	// 토큰으로 이름 찾기?
            username = jwtTokenUtil.getUsernameFromToken(accessToken);
        } catch (IllegalArgumentException e) {} catch (ExpiredJwtException e) { //expire됐을 때
            username = e.getClaims().getSubject();
            return new ResponseEntity<String>("fail",HttpStatus.NO_CONTENT);
        }

        try {
            if (redisTemplate.opsForValue().get(username) != null) {
                //delete refresh token
                redisTemplate.delete(username);
            }
        } catch (IllegalArgumentException e) {
        	return new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
        }

        //cache logout token for 10 minutes!
        logger.info(" logout ing : " + accessToken);
        redisTemplate.opsForValue().set(accessToken, true);
        redisTemplate.expire(accessToken, 10*6*1000, TimeUnit.MILLISECONDS);

        return new ResponseEntity<String>("success",HttpStatus.OK);
    }
    
    @ApiOperation(value = "회원가입")
    @PostMapping(path="/user/signup")
    public Map<String, Object> addNewUser (@RequestBody SignUpDto signup) {
        String email = signup.getName();
        Map<String, Object> map = new HashMap<>();
        System.out.println("회원가입요청 아이디: "+email + "비번: " + signup.getPassword());
        if (accountRepository.findByEmail(email) == null) {        	            
        	Member member = new Member();        	
        	if (signup.getRole() != null && signup.getRole().equals("admin")) {
        		member.setRole("ROLE_ADMIN");
            } else {
            	member.setRole("ROLE_USER");
            }
        	member.setPassword(bcryptEncoder.encode(signup.getPassword()));
        	member.setEmail(email);
        	member.setName(signup.getName());
        	member.setThumbnail(signup.getThumbnail());
        	
            map.put("success", true);
            accountRepository.save(member);
            return map;
        } else {
            map.put("success", false);
            map.put("message", "duplicated email");
        }
        return map;
    }
    
    
    @ApiOperation(value = "관리자용 회원탈퇴")
    @Transactional
    @DeleteMapping(path="/admin/delete")
    public ResponseEntity<?> deleteAdminUser (@RequestParam String email) {
        logger.info("delete user: " +email);
        try {
        	 accountRepository.deleteByEmail(email);
        }catch (Exception e) {
        	 return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
		}      
        return new ResponseEntity<String>("success",HttpStatus.OK);
    }
    
    @ApiOperation(value = "회원탈퇴")
    @Transactional
    @DeleteMapping(path="/user/delete")
    public ResponseEntity<?> deleteUser (@RequestBody String accessToken) {
    	String email = null; 
    	try {
    		email = jwtTokenUtil.getUsernameFromToken(accessToken);
         } catch (IllegalArgumentException e) {} catch (ExpiredJwtException e) { //expire됐을 때
        	 email = e.getClaims().getSubject();
        	 return new ResponseEntity<String>("fail", HttpStatus.NO_CONTENT);
         }
    	       
        try {
            if (redisTemplate.opsForValue().get(email) != null) {
                //delete refresh token
                redisTemplate.delete(email);
            }
        } catch (IllegalArgumentException e) {
        	return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
        }

        //cache logout token for 10 minutes!
        logger.info(" logout ing : " + accessToken);
        redisTemplate.opsForValue().set(accessToken, true);
        redisTemplate.expire(accessToken, 10*6*1000, TimeUnit.MILLISECONDS);
        
        logger.info("delete user: " + email);
        Long result = accountRepository.deleteByEmail(email);
        logger.info("delete result: " + result);
        
        return new ResponseEntity<String>("success",HttpStatus.OK);
        
    }
    
    @ApiOperation(value = "관리자용 회원 전체조회")
    @GetMapping(path="/admin/getusers")
    public Iterable<Member> getAllMember() {
        return accountRepository.findAll();
    }
    
    @ApiOperation(value = "회원정보수정")
    @Transactional
    @PutMapping(path="/user/update")
    public ResponseEntity<?> UpdateMember(@RequestBody UpdateMemberDto update) {  	
    	Member member = accountRepository.findByEmail(update.getEmail());
    	if(member == null) new ResponseEntity<String>("fail",HttpStatus.NO_CONTENT);
    	member.setName(update.getName());
    	member.setThumbnail(update.getThumbnail());
    	try {
    		accountRepository.save(member);
    	}catch (Exception e) {
    		new ResponseEntity<String>("fail",HttpStatus.BAD_REQUEST);
		}
    	
    	return new ResponseEntity<String>("success",HttpStatus.OK);
    }
    
    @ApiOperation(value = "이메일 중복 체크")
    @GetMapping(path="/user/checkemail")
    public boolean checkEmail (@RequestParam String email) {
        System.out.println("이메일체크 요청 이메일: " +email);
        if (accountRepository.findByEmail(email) == null) return true;
        else return false;
    }
    	
    
    @ApiOperation(value = "로그인 연장")
    @PostMapping(path="/user/refresh")
    public Map<String, Object>  requestForNewAccessToken(@RequestBody Map<String, String> m) {
        String accessToken = null;
        String refreshToken = null;
        String refreshTokenFromDb = null;
        String email = null;
        Map<String, Object> map = new HashMap<>();
        try {
            accessToken = m.get("accessToken");
            refreshToken = m.get("refreshToken");
            logger.info("access token in rnat: " + accessToken);
            try {
            	email = jwtTokenUtil.getUsernameFromToken(accessToken);
            } catch (IllegalArgumentException e) {

            } catch (ExpiredJwtException e) { //expire됐을 때
            	System.out.println("만료된 토큰 이였습니다");
            	email = e.getClaims().getSubject();
                logger.info("username from expired access token: " + email);
            }

            if (refreshToken != null) { //refresh를 같이 보냈으면.
                try {
                    ValueOperations<String, Object> vop = redisTemplate.opsForValue();
                    Token result = (Token) vop.get(email);
                    refreshTokenFromDb = result.getRefreshToken();
                    
                    logger.info("rtfrom db: " + refreshTokenFromDb);
                } catch (IllegalArgumentException e) {
                    logger.warn("illegal argument!!");
                }
                //둘이 일치하고 만료도 안됐으면 재발급 해주기.
                if (refreshToken.equals(refreshTokenFromDb) && !jwtTokenUtil.isTokenExpired(refreshToken)) {
                    final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                    String newtok =  jwtTokenUtil.generateAccessToken(userDetails);
                    map.put("success", true);
                    map.put("accessToken", newtok);
                } else {
                    map.put("success", false);
                    map.put("msg", "refresh token is expired.");
                }
            } else { //refresh token이 없으면
                map.put("success", false);
                map.put("msg", "your refresh token does not exist.");
            }

        } catch (Exception e) {
            throw e;
        }
        logger.info("m: " + m);

        return map;
    }
    
//    @PostMapping(path="/user/check")
//    public Map<String, Object> checker(@RequestBody Map<String, String> m) {
//        String username = null;
//        Map<String, Object> map = new HashMap<>();
//        try {
//            username = jwtTokenUtil.getUsernameFromToken(m.get("accessToken"));
//        } catch (IllegalArgumentException e) {
//            logger.warn("Unable to get JWT Token");
//        }
//        catch (ExpiredJwtException e) {
//        }
//
//        if (username != null) {
//            map.put("success", true);
//            map.put("username", username);
//        } else {
//            map.put("success", false);
//        }
//        return map;
//    }
    
//    @ApiOperation(value = "일반회원 이거 되나?용")
//    @GetMapping(path="/user/normal")
//    public ResponseEntity<?> onlyNormal() {
//        return new ResponseEntity(HttpStatus.OK);
//    }
    
}