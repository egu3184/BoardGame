package com.egu.boot.BoardGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
public class BoardGameApplication extends SpringBootServletInitializer  {

	public static void main(String[] args) {
		SpringApplication.run(BoardGameApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BoardGameApplication.class);
	}



	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
	   return new BCryptPasswordEncoder();
	}
		
	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}
	 
	
}
