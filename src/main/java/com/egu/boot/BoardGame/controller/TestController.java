package com.egu.boot.BoardGame.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@GetMapping("/test")
	public String test() {
		System.out.println("Test에용");
		return "<h1> DB 생성 확인 <h1>";
	}
	
	
}
