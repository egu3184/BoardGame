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

		//(1)요청의 헤더로부터 AccessToken 꺼내기
		String token = jwtTokenProvider.resolveAccessToken(request);
		System.out.println("필터에서 토큰 확인 = "+token);
		
		Boolean result =null;
		//(2-B) 토큰이 담긴 요청시 
		if(token != null) {
			//(3) 토큰 유효성 확인 ->  유효하지 않다면 validateToken 메서드에서 exception 발생
			result = jwtTokenProvider.validateAccessToken(response, token);
			//(4) 토큰이 유효하면
			if(result == true) {
				//jwt 토큰에 저장된 userId로 Authentication 얻기
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				// 인증 정보(Authentication)를 스레드 내 저장소(SecurityContextHolder)에 저장.
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}
		//(2-A) 토큰 없는 요청시
		if(result == null || result == true) {
			filterChain.doFilter(request, response);
		}
		
	}
	
	
	
}
