package com.egu.boot.BoardGame.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.egu.boot.BoardGame.service.CustomOauth2UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.cors().disable()
			.httpBasic().disable() // header에 (base64로 인코딩된) id와 pw를 받아 인증하는 방식.
			.csrf().disable()	//rest api로 csrf 보안 필요없음.
			.authorizeRequests()
				.antMatchers("/user/**").authenticated()	 // /user/가 붙으면 인증된 유저만
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // /admin/이 붙으면 관리자만
				.antMatchers(HttpMethod.OPTIONS	, "/**").permitAll()
				.anyRequest().permitAll() //나머지는 모두 허용
			.and()
				//JWT Token필터를 id/password 인증 필터 전에 넣음.
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
				
	}
	
	
	
}
