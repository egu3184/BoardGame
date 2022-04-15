package com.egu.boot.BoardGame.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtInterceptor implements HandlerInterceptor {

	private final JwtTokenProvider jwtTokenProvider;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("인터셉터당");
		if(request.getAttribute("error") != null) {
			System.out.println("인터셉터에서 error 캐치");
			System.out.println(request.getAttribute("error"));
			if(request.getAttribute("error") == ErrorCode.EXPIRED_TOKEN.getMessage()) {
				System.out.println("??");
			}	
		}
		return true;
	}

		
	
	
}
