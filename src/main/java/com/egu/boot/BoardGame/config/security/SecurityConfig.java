package com.egu.boot.BoardGame.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.egu.boot.BoardGame.config.CorsConfigFilter;
import com.egu.boot.BoardGame.handler.CustomJwtExceptionHandler;
import com.egu.boot.BoardGame.service.CustomOauth2UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;
	private final CorsConfigFilter corsfilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.addFilter(corsfilter.filter())
//			.cors().disable()
			.httpBasic().disable() // header에 (base64로 인코딩된) id와 pw를 받아 인증하는 방식.
			.csrf().disable()	//rest api로 csrf 보안 필요없음.
			//@PreAuthorize, @Secured 같은 어노테이션으로 권한 관리 가능 -> BUT 시큐리티 필터에서 예외처리 X, 사용 안하기로 
//			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  
			.authorizeRequests()
				.antMatchers("/user/**").authenticated()	 // /user/가 붙으면 인증된 유저만
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // /admin/이 붙으면 관리자만
				.antMatchers(HttpMethod.OPTIONS	, "/**").permitAll() //Options 허용
				.anyRequest().permitAll() //나머지는 모두 허용
			.and()
				// Jwt 토큰이 없거나 형식이 맞지 않거나 만료된 경우
				.exceptionHandling().authenticationEntryPoint(new CustomJwtExceptionHandler.CustomAuthenticationEntryPoint())
			.and()	
				.exceptionHandling().accessDeniedHandler(new CustomJwtExceptionHandler.CustomAccessDeniedHandler())
				//Jwt Token필터를 id/password 인증 필터 전에 넣음.
			.and()	
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
			
	}
	
	
	
}
