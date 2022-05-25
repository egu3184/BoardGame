package com.egu.boot.BoardGame.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
import com.egu.boot.BoardGame.handler.CustomJwtExceptionHandler.CustomAuthenticationEntryPoint;

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
//			if(request.getAttribute("error") != null) {
//				System.out.println("인터셉터에서 error 캐치");
//				if(request.getAttribute("error") == ErrorCode.EXPIRED_TOKEN.getMessage()) {
//					System.out.println("토큰 만료");
//					CustomJwtExceptionHandler.setResponse(ErrorCode.EXPIRED_TOKEN, response);
//					return false;
//				}else if(request.getAttribute("error") == ErrorCode.INVALID_TOKEN.getMessage()){
//					CustomJwtExceptionHandler.setResponse(ErrorCode.INVALID_TOKEN, response);
//					return false;
//				}else if(request.getAttribute("error") == ErrorCode.UNKNOWN.getMessage()) {
//					CustomJwtExceptionHandler.setResponse(ErrorCode.UNKNOWN, response);
//					return false;
//				}else {
//					
//					return false;
//				}
//			}
			return true;
			
	}

		
	
	
}
