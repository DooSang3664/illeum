package com.ssafy.pjt.service;

import com.ssafy.pjt.dto.Member;
import com.ssafy.pjt.repository.MemberRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = repository.findByEmail(email);

        List<GrantedAuthority> roles = new ArrayList<>();

        if (member == null) {
            throw new UsernameNotFoundException("User not found with username: " + email);
        }
        if ((member.getRole()).equals("ROLE_ADMIN")) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
            roles.add(new SimpleGrantedAuthority("ROLE_HI"));
        }
        return new User(member.getEmail(), member.getPassword(), roles);
    }

}
