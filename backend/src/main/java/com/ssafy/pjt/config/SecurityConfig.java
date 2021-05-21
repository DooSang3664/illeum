package com.ssafy.pjt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ssafy.pjt.jwt.JwtAuthenticationEntryPoint;
import com.ssafy.pjt.jwt.JwtRequestFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;
	@Autowired
	private UserDetailsService jwtUserDetailsService;
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception { // NOSONAR : required throw generic Exception.

		http.httpBasic().disable().cors().and().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).

				and().authorizeRequests().antMatchers("/swagger-resources/**").permitAll().// swagger
				antMatchers("/swagger-ui.html").permitAll().antMatchers("/v2/api-docs").permitAll()
				.antMatchers("/webjars/**").permitAll().

//				and().authorizeRequests().antMatchers("/api/**").permitAll().
				and().
                   authorizeRequests().
                   antMatchers("/api/image/**").permitAll().
		
			    and().
                   authorizeRequests().
                   antMatchers("/api/member/user/login").permitAll().
                and().
                   authorizeRequests().
                   antMatchers("/api/member/user/logout").permitAll().
                and().
                   authorizeRequests().
                   antMatchers("/api/member/user/signup").permitAll().   
                and().
                   authorizeRequests().
                   antMatchers("/api/member/user/checkemail").permitAll().
                and().
                   authorizeRequests().
                   antMatchers("/api/member/user/refresh").hasAnyRole("USER", "ADMIN").
				  and().
                   authorizeRequests().
                   antMatchers("/api/member/admin/**").hasRole("ADMIN").
                and().
                   authorizeRequests().
                   antMatchers("/api/member/user/**").hasRole("USER").
                and().
                   authorizeRequests().
                   antMatchers("/api/member/profile/**").hasAnyRole("USER", "ADMIN").
                     
                and().
                    authorizeRequests().
                    antMatchers("/api/room/**").hasAnyRole("USER", "ADMIN").
                    
                and().
                    authorizeRequests().
                    antMatchers("/api/entrant/**").hasAnyRole("USER", "ADMIN").
                    
                and().             
                    authorizeRequests().
                    antMatchers("/api/evaluation/**").hasAnyRole("USER", "ADMIN").
                and().             
                    authorizeRequests().
                    antMatchers("/api/rtc/**").hasAnyRole("USER", "ADMIN").    
                   

				and().authorizeRequests().anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(jwtAuthenticationEntryPoint).and()
				.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
