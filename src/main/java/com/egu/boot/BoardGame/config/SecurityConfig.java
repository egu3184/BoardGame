package com.egu.boot.BoardGame.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.egu.boot.BoardGame.service.CustomOauth2UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	//@Autowired
	//private CustomOauth2UserService customOauth2UserService;
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated()
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
		.and() 
			.formLogin()
				.loginPage("/loginForm")
				.loginProcessingUrl("/login")
					.defaultSuccessUrl("/main")
		.and()
			.logout()
				.logoutSuccessUrl("/main");
	/*
		.and()
			.oauth2Login()				//소셜 설정 진입점
				.loginPage("/loginForm")
				.userInfoEndpoint()	//로그인 성공 시 후속 조치할 UserService인터페이스의 구현체를 등록
					.userService(customOauth2UserService);
	*/				
	}
	
	
}
