	package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	public CommonResult signup(@RequestBody User requestUser) {
		User user = userService.회원가입(requestUser);
		return responseService.getSingleResult(user);
	}
	
	@GetMapping("/member/{id}")
	public CommonResult findUser(@PathVariable int id) {
		User user = userService.회원찾기(id);
		return responseService.getSingleResult(user);
	}
	
	@GetMapping("/member")
	public CommonResult findAllUser(
			@PageableDefault(direction = Direction.DESC, sort = "id") Pageable pageable) {
		Page<User> list = userService.회원리스트찾기(pageable);
		return responseService.getPageListResult(list);
	}
	
	@PutMapping("/member")
	public CommonResult editUser(@RequestBody User requestUser) {
		userService.회원수정(requestUser);
		return null;
	}
	
	
}
