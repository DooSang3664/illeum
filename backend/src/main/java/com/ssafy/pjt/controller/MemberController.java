package com.ssafy.pjt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.re2j.Pattern;
import com.ssafy.pjt.dto.Member;
import com.ssafy.pjt.dto.Token;
import com.ssafy.pjt.dto.request.LoginDto;
import com.ssafy.pjt.dto.request.SignUpDto;
import com.ssafy.pjt.dto.request.UpdateMemberDto;
import com.ssafy.pjt.jwt.JwtTokenUtil;
import com.ssafy.pjt.repository.MemberRepository;
import com.ssafy.pjt.repository.mapper.MemberMapper;
import com.ssafy.pjt.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api/member") // This means URL's start with /demo (after Application path)
public class MemberController {
	private static final String ACCESSTOKEN = "accessToken";
	private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private MemberMapper memberMapper;
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
	@Autowired
	private AmqpAdmin admin;

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@ApiOperation(value = "?????????")
	@PostMapping(path = "/user/login")
	public ResponseEntity<Object> login(@RequestBody LoginDto login) { // NOSONAR
		final String email = login.getEmail();
		logger.info("test input username: {}", email);
		// ????????? ????????? ??????
		if (memberRepository.findByEmail(email) == null)
			return new ResponseEntity<>("not register", HttpStatus.OK);
		// ???????????? ??????
		try {
			am.authenticate(new UsernamePasswordAuthenticationToken(email, login.getPassword()));
		} catch (Exception e) {
			return new ResponseEntity<>("wrong password", HttpStatus.OK);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
		final String refreshToken = jwtTokenUtil.generateRefreshToken(email);

		Token retok = new Token();
		retok.setUsername(email);
		retok.setRefreshToken(refreshToken);

		// generate Token and save in redis
		ValueOperations<String, Object> vop = redisTemplate.opsForValue();
		vop.set(email, retok);

		logger.info("generated access token: {}",  accessToken);
		logger.info("generated refresh token: {}", refreshToken);
		Map<String, Object> map = new HashMap<>();
		map.put(ACCESSTOKEN, accessToken);
		map.put("refreshToken", refreshToken);
		try {
			Member member = memberRepository.findByEmail(email);
			member.setPassword("");
			map.put("member", member);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "????????????")
	@PostMapping(path = "/user/logout")
	public ResponseEntity<Object> logout(@RequestBody String accessToken) {
		String username = null;
		try {
			// ???????????? ?????? ???????
			username = jwtTokenUtil.getUsernameFromToken(accessToken);
		} catch (IllegalArgumentException | ExpiredJwtException e) { // expire?????? ???
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		}

		try {
			if (redisTemplate.opsForValue().get(username) != null) {
				// delete refresh token
				redisTemplate.delete(username);
			}
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}

		// cache logout token for 10 minutes!
		logger.info(" logout ing : {}", accessToken);
		redisTemplate.opsForValue().set(accessToken, true);
		redisTemplate.expire(accessToken, 10 * 6 * 1000, TimeUnit.MILLISECONDS);

		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
	}

	@ApiOperation(value = "????????????")
	@PostMapping(path = "/user/signup")
	public ResponseEntity<Object> addNewUser(@RequestBody SignUpDto signup) {
		Pattern regExp = Pattern.compile(
				"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
		String email = signup.getEmail();
		if (!regExp.matcher(email).matches())
			return new ResponseEntity<>("????????? ?????? ??????", HttpStatus.BAD_REQUEST); // NOSONAR : safe here. a regular
																				// expression library google/re2.
		Map<String, Object> map = new HashMap<>();
		if (memberRepository.findByEmail(email) == null) {
			Member member = new Member();
			if (signup.getRole() != null && signup.getRole().equals("admin")) {
				member.setRole("ROLE_ADMIN");
			} else {
				member.setRole("ROLE_USER");
			}
			member.setPassword(bcryptEncoder.encode(signup.getPassword()));
			member.setEmail(email);
			member.setName(signup.getName());

			map.put(SUCCESS, true);
			Member mem = memberRepository.save(member);

			String queueName = "member." + Integer.toString(mem.getUid());
			Queue queue = new Queue(queueName, false);
			admin.declareQueue(queue);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			map.put(SUCCESS, false);
			map.put("message", "duplicated email");
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@ApiOperation(value = "???????????? ????????????")
	@Transactional
	@DeleteMapping(path = "/admin/delete")
	public ResponseEntity<Object> deleteAdminUser(@RequestParam String email) {
		try {
			memberRepository.deleteByEmail(email);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
	}

	@ApiOperation(value = "????????????")
	@Transactional
	@DeleteMapping(path = "/user/delete")
	public ResponseEntity<Object> deleteUser(@RequestBody String accessToken) {
		String email = null;
		try {
			email = jwtTokenUtil.getUsernameFromToken(accessToken);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		} catch (ExpiredJwtException e) { // expire?????? ???
			email = e.getClaims().getSubject();
			logger.info("username from expired access token: {}" , email);
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);
		}

		Member mem = memberRepository.findByEmail(email);
		try {

			if (redisTemplate.opsForValue().get(email) != null) {
				// delete refresh token
				redisTemplate.delete(email);
			}
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}

		String queueName = "member." + Integer.toString(mem.getUid());
		admin.deleteQueue(queueName);

		// cache logout token for 10 minutes!
		logger.info(" logout ing : {}" , accessToken);
		redisTemplate.opsForValue().set(accessToken, true);
		redisTemplate.expire(accessToken, 10 * 6 * 1000, TimeUnit.MILLISECONDS);

		logger.info("delete user: {}" , email);
		Long result = memberRepository.deleteByEmail(email);
		logger.info("delete result: {}" , result);

		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);

	}

	@ApiOperation(value = "???????????? ?????? ????????????")
	@GetMapping(path = "/admin/getusers")
	public Iterable<Member> getAllMember() {
		return memberRepository.findAll();
	}

	@ApiOperation(value = "uid??? ???????????? ??????")
	@GetMapping(path = "/user/findByUid")
	public ResponseEntity<Object> findByUid(@RequestParam int uid) {
		try {
			Member member = memberRepository.findByUid(uid);
			if (member == null)
				return new ResponseEntity<>("??????  uid??? ?????????????????????.", HttpStatus.NO_CONTENT);
			return new ResponseEntity<>(member, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "????????? ????????? ?????????")
	@GetMapping(path = "/user/room")
	// ????????? ????????? ???????????? ?????? ?????? ???????????? uid ?????? ????????? ????????? ??????
	public ResponseEntity<Object> memberJoinRoom(@RequestParam int uid) {
		List<Map<String, Object>> list = null;
		try {
			list = memberMapper.memberJoinRoom(uid);
			if (list.isEmpty())
				return new ResponseEntity<>("?????? ?????? ????????? ????????????.", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "?????? ????????????")
	@GetMapping(path = "/user/attend")
	// ????????? ????????? ???????????? ?????? ?????? ???????????? uid ?????? ????????? ????????? ??????
	public ResponseEntity<Object> memberAttend(@RequestParam int uid) {
		List<Map<String, Object>> list = null;
		try {
			list = memberMapper.memberAttend(uid);
			if (list.isEmpty())
				return new ResponseEntity<>("?????? ????????? ????????????.", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "?????????  ????????? ??? ??????")
	@GetMapping(path = "/user/founder")
	// ????????? ????????? ???????????? ?????? ?????? ???????????? uid ?????? ????????? ????????? ??????
	public ResponseEntity<Object> founder(@RequestParam int uid) {
		List<Map<String, Object>> list = null;
		try {
			list = memberMapper.founder(uid);
			if (list.isEmpty())
				return new ResponseEntity<>("????????? ?????? ????????????.", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "????????? ?????? ??????")
	@GetMapping(path = "/user/evaluation")
	// ????????? ????????? ???????????? ?????? ?????? ???????????? uid ?????? ????????? ????????? ??????
	public ResponseEntity<Object> memberJoinEvaluation(@RequestParam int uid) {
		List<Map<String, Object>> list;
		try {
			list = memberMapper.memberJoinEvaluation(uid);
			if (list.isEmpty())
				return new ResponseEntity<>("????????? ????????????", HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@ApiOperation(value = "??????????????????")
	@Transactional
	@PutMapping(path = "/user/update")
	public ResponseEntity<Object> updateMember(@RequestBody UpdateMemberDto update) {
		Member member = memberRepository.findByEmail(update.getEmail());
		if (member == null)
			return new ResponseEntity<>(FAIL, HttpStatus.NO_CONTENT);

		if (update.getPassword() != null)
			member.setPassword(bcryptEncoder.encode(update.getPassword()));
		if (update.getName() != null)
			member.setName(update.getName());

		try {
			memberRepository.save(member);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
	}

	@ApiOperation(value = "????????? ?????? ??????")
	@GetMapping(path = "/user/checkemail")
	public ResponseEntity<Object> checkEmail(@RequestParam String email) {
		try {
			if (memberRepository.findByEmail(email) == null)
				return new ResponseEntity<>(true, HttpStatus.OK);
			else
				return new ResponseEntity<>(false, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(FAIL, HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "????????? ??????")
	@PostMapping(path = "/user/refresh")
	public Map<String, Object> requestForNewAccessToken(@RequestBody Map<String, String> m) throws ServletException {
		String accessToken = null;
		String refreshToken = null;
		String refreshTokenFromDb = null;
		String email = null;
		Map<String, Object> map = new HashMap<>();
		try {
			accessToken = m.get(ACCESSTOKEN);
			refreshToken = m.get("refreshToken");
			logger.info("access token in rnat: {}" , accessToken);

			email = jwtTokenUtil.getUsernameFromToken(accessToken);

			if (refreshToken != null) { // refresh??? ?????? ????????????.

				ValueOperations<String, Object> vop = redisTemplate.opsForValue();
				Token result = (Token) vop.get(email);
				refreshTokenFromDb = result.getRefreshToken();

				logger.info("rtfrom db: {}", refreshTokenFromDb);

				// ?????? ???????????? ????????? ???????????? ????????? ?????????.
				if (refreshToken.equals(refreshTokenFromDb) && !jwtTokenUtil.isTokenExpired(refreshToken)) {
					final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
					String newtok = jwtTokenUtil.generateAccessToken(userDetails);
					map.put(SUCCESS, true);
					map.put(ACCESSTOKEN, newtok);
				} else {
					map.put(SUCCESS, false);
					map.put("msg", "refresh token is expired.");
				}
			} else { // refresh token??? ?????????
				map.put(SUCCESS, false);
				map.put("msg", "your refresh token does not exist.");
			}

		} catch (ExpiredJwtException e) { // expire?????? ???
			logger.info("????????? ?????? ???????????????");
			email = e.getClaims().getSubject();
			logger.info("username from expired access token: {}" , email);
		} catch (Exception e) {
			throw new ServletException(e);
		}
		logger.info("m: {}" , m);

		return map;
	}
}