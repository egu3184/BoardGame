package com.egu.boot.BoardGame.handler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;

public class CustomJwtExceptionHandler{
	
	@Component
	public static class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException authException) throws IOException, ServletException {
			System.out.println("2");
//			if(request.getAttribute("error") == ErrorCode.EXPIRED_TOKEN.getMessage()) {
//				setResponse(ErrorCode.EXPIRED_TOKEN, response);
//			}else if(request.getAttribute("error") == ErrorCode.INVALID_TOKEN.getMessage()){
//				setResponse(ErrorCode.INVALID_TOKEN, response);
//			}else if(request.getAttribute("error") == ErrorCode.UNKNOWN.getMessage()) {
//				setResponse(ErrorCode.UNKNOWN, response);
//			}else { // 인증되지 않은 유저가 요청했을 때
//				setResponse(ErrorCode.FORBBIDDEN, response);
//			}
//			request.setAttribute("error", null);
		}
	}
	
	public static void setResponse(ErrorCode error, HttpServletResponse response) throws IOException {
		JsonObject json = new JsonObject();
		json.addProperty("code", error.getCode());
		json.addProperty("message", error.getMessage());
		PrintWriter out =  response.getWriter();
		out.print(json);
//		out.flush();
//		out.close();
	}
	
	@Component
	public static class CustomAccessDeniedHandler implements AccessDeniedHandler{

		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response,
				AccessDeniedException accessDeniedException) throws IOException, ServletException {
			System.out.println("권한 체크");
			setResponse(ErrorCode.ACCESS_DENIED, response);
		}
		
	}

	
}
