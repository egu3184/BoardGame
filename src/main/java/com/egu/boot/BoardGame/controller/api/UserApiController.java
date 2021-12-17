	package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/join")
	public String save(@RequestBody User user) {
		
		userService.회원가입(user);
	
		return "<h1>가입 완료!<h1>";
		//나중에 프론트로 return  값 설정해주기
	}
	
}
