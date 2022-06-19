	package com.egu.boot.BoardGame.controller.api;


import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.api.SingleResult;
import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationResponseDto;
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
	
	@GetMapping("/users")
	public CommonResult findUserByUserInfo(
											@ModelAttribute UserRequestDto requestDto){
		User user  = userService.회원정보로찾기(requestDto);
		return (user != null ) ? responseService.getSingleResult(new UserResponseDto(user)) : responseService.getFailResult(ErrorCode.USER_NOT_FOUND);
	}
	
	@PutMapping("/users")
	public SingleResult<UserResponseDto> modifyUserInfo(
											@RequestBody UserRequestDto requestDto){
		UserResponseDto responseDto = userService.회원정보수정(requestDto);
		return responseService.getSingleResult(responseDto);
	}
	
	@DeleteMapping("/users")
	public CommonResult deactivateUser(
											@RequestBody UserRequestDto requestDto){
		if(requestDto.getPassword() == null) { return responseService.getSingleResult(ErrorCode.USERINFO_NOT_ENOUGH); }
		long result = userService.회원탈퇴(requestDto);
		return result > 0 ? responseService.getSuccessResult() : responseService.getFailResult(ErrorCode.USERINFO_CHANGE_FAILED);
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
	
	
}
