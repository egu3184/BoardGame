package com.egu.boot.BoardGame.controller;
import org.hibernate.service.spi.ServiceException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/test")
	public String test() {
		System.out.println("Test에용");
		return "<h1> 테스트 테스트 <h1>";
	}
	

	
	
}
