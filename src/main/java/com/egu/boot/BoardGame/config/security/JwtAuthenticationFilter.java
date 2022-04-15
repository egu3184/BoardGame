package com.egu.boot.BoardGame.config.security;

import java.io.IOException;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		//요청의 헤더로부터 AccessToken 꺼내기
		String token = jwtTokenProvider.resolveAccessToken(request);
		
		//토큰 유효성 확인
		//->  유효하지 않다면 validateToken 메서드에서 exception 발생 -> 
		if(token != null && jwtTokenProvider.validateAccessToken(request, token)) {
			
			//jwt 토큰에 저장된 userId로 Authentication 얻기
			Authentication auth = jwtTokenProvider.getAuthentication(token);
			
			// 인증 정보(Authentication)를 스레드 내 저장소(SecurityContextHolder)에 저장.
			// 이후 스레드에서 필요시 꺼내서 사용하게 됨.
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		filterChain.doFilter(request, response);
	}
	
	
	
}
