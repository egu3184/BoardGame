	package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.service.UserService;
import com.egu.boot.BoardGame.service.api.ResponseService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ResponseService responseService;
	
	@PostMapping("/auth/signup")
	public CommonResult save(@RequestBody User requestUser) {
		User user = userService.회원가입(requestUser);
		return responseService.getSingleResult(user);
	}
	
	@GetMapping
	public CommonResult findUser() {
		
		return responseService.getSingleResult(null);
	}
	
}
