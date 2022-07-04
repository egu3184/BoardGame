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
//import com.egu.boot.BoardGame.config.CorsConfigFilter;
import com.egu.boot.BoardGame.handler.CustomJwtExceptionHandler;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity	//시큐리티를 활성화 하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenProvider jwtTokenProvider;
	private final CorsConfigFilter corsfilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.addFilter(corsfilter.filter())
			.cors().disable()
			.httpBasic().disable()									 	// header에 (base64로 인코딩된) id와 pw를 받아 인증하는 방식.
			.csrf().disable()													 //rest api로 csrf 보안 필요없음.
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //세션 사용 x
			.and()
			.authorizeRequests()										//HttpServletRequest를 사용하는 요청에 대한 접근제한을 하겠다.
				.antMatchers("/users/**").authenticated()	 // 인증된 유저만 -> 아니라면 AuthenticationEntryPoint
				.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')") // 관리자만
				.antMatchers(HttpMethod.OPTIONS	, "/**").permitAll() //Options 허용
				.anyRequest().permitAll()								 //나머지는 모두 허용
//				.and() 																//Authentication(인증)문제, 인증되지 않은 유저가 요청했을 때 혹은 jwt 토큰 에러가 났을 때
//					.exceptionHandling().authenticationEntryPoint(new CustomJwtExceptionHandler.CustomAuthenticationEntryPoint())
				.and()																//Authorization(인가), 인증은 되었지만 권한이 없을 때 
					.exceptionHandling().accessDeniedHandler(new CustomJwtExceptionHandler.CustomAccessDeniedHandler())
			.and()																	// Jwt Token필터를 id/password 인증 필터 전에 넣음.	
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
			;
	}
	
	
	
}
