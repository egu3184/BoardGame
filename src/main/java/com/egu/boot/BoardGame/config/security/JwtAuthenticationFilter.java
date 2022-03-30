package com.egu.boot.BoardGame.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean{

	private final JwtTokenProvider jwtTokenProvider;
	
	//Request로 들어오는 Jwt Token의 유효성 검증하는
	//JwtTokenProvider의 validateToken()을 filterChain에 등록
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//요청의 헤더로부터 token 파싱("X-AUTH-TOKEN")
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		
		//토큰이 null이 아니고 토큰이 유효하다면
		if(token != null && jwtTokenProvider.validateToken(token)) {
			
			//토큰으로 인증 조회
			Authentication auth = jwtTokenProvider.getAuthentication(token);
			
			// 인증 정보(Authentication)를 스레드 내 저장소(SecurityContextHolder)에 저장.
			// 이후 스레드에서 필요시 꺼내서 사용하게 됨.
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		chain.doFilter(request, response);
	}

	
	
}
