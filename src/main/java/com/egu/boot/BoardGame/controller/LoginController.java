package com.egu.boot.BoardGame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

	@GetMapping("login")
	public String loginForm() {
		return "loginForm";
	}
	
	
}
