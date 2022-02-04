package com.egu.boot.BoardGame.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer{

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		
		registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("*")
						.maxAge(3000);
	}
}
