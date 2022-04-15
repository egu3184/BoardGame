	package com.egu.boot.BoardGame.controller.api;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.api.SingleResult;
import com.egu.boot.BoardGame.model.dto.UserDto.UserRequestDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserResponseDto;
import com.egu.boot.BoardGame.service.UserService;
import com.egu.boot.BoardGame.service.api.ResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserApiController {

	private final UserService userService;
	private final ResponseService responseService;
	private final JwtTokenProvider jwtTokenProvider;
	
	
	@PostMapping("/login")
	public SingleResult<UserResponseDto> login(@RequestBody UserRequestDto requestDto){
		UserResponseDto responseDto = userService.로그인(requestDto.getUserId(), requestDto.getPassword());
		return responseService.getSingleResult(responseDto);
	}
	
	@PostMapping("/signup")
	public CommonResult signup(@RequestBody UserRequestDto requestDto) {
		User user = userService.회원가입(requestDto);
		return responseService.getSuccessResult();
	}
	
	@GetMapping("/user/user")
	public SingleResult<UserResponseDto> findUser(){
		System.out.println("여기까지 오긴 하니");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		System.out.println("authentication" + authentication);
//		System.out.println("authentication의 username은?"+((User) authentication.getPrincipal()).getUserId());
//		User user  = userService.회원찾기(authentication.getName());
		UserResponseDto user  = userService.회원찾기(((User) authentication.getPrincipal()).getUserId());
		
		return responseService.getSingleResult(user);
		
	}
	@GetMapping("/admin/test")
	public SingleResult<String> test(){
		System.out.println("admin만 들어올 수 있는 곳인데에엣");
		return responseService.getSingleResult("권한 체크! : 뜨면 뚫린 거");
	}
	@GetMapping("/user/test")
	public SingleResult<String> testuser(){
		System.out.println("user 이상만 들어올 수 있는 곳인데에엣");
		return responseService.getSingleResult("권한 체크! : 뜨면 뚫린 거");
	}
	
	
//	@GetMapping("/admin/{id}")
//	public CommonResult findUser(@PathVariable int id) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User user = userService.회원찾기(id);
//		return responseService.getSingleResult(user);
//	}
	
//	@GetMapping("/member")
//	public CommonResult findAllUser(
//			@PageableDefault(direction = Direction.DESC, sort = "id") Pageable pageable) {
//		Page<User> list = userService.회원리스트찾기(pageable);
//		return responseService.getPageListResult(list);
//	}
//	
//	@PutMapping("/member")
//	public CommonResult editUser(@RequestBody User requestUser) {
//		userService.회원수정(requestUser);
//		return null;
//	}
	
	
}
