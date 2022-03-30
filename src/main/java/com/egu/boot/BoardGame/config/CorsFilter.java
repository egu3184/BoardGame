package com.egu.boot.BoardGame.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class CorsFilter implements Filter {
//
//	@Override
//	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) resp;
//		
//		response.setHeader("Acess-Control-Allow-Origin", "http://localhost:8081");
//		response.setHeader("Acess-Control-Allow-Credentials", "true");
//		response.setHeader("Acess-Control-Max-Age", "3600");
//		response.setHeader("Acess-Control-Allow-Headers", 
//				"Origin, X-AUTH-TOKEN, X-Requested-With, Content-Type, Accept, Authorization");
//		if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
//			System.out.println("옵션스에 들어옴");
//			chain.doFilter(req, resp);
////			response.setStatus(HttpServletResponse.SC_OK);
//		}else {
//			chain.doFilter(req, resp);
//		}
//	}
//
//	
//	
//	
//}
