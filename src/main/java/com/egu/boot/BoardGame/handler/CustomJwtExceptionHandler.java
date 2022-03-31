package com.egu.boot.BoardGame.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.service.api.ResponseService;

import lombok.RequiredArgsConstructor;


public class CustomJwtExceptionHandler{

	@Component
	public static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{
		
		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
			response.sendRedirect("/exception/entrypoint");
		}
	}
	
	@Component
	public static class CustomAccessDeniedHandler implements AccessDeniedHandler{

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response,
				AccessDeniedException accessDeniedException) throws IOException, ServletException {
			response.sendRedirect("/exception/accessdenied");
			
		}
		
	}

	
}
