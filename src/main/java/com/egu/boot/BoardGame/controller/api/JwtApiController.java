package com.egu.boot.BoardGame.controller.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
import com.egu.boot.BoardGame.model.api.SingleResult;
import com.egu.boot.BoardGame.model.dto.TokenDto.TokenRequestDto;
import com.egu.boot.BoardGame.model.dto.TokenDto.TokenResponseDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserRequestDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserResponseDto;
import com.egu.boot.BoardGame.service.TokenService;
import com.egu.boot.BoardGame.service.api.ResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JwtApiController {
	
	private final ResponseService responseService;
	private final TokenService tokenService;
	
	@PostMapping("/reissue")
	public SingleResult<TokenResponseDto> tokenReissue(@RequestBody TokenRequestDto requestDto
			,HttpServletRequest request){
		System.out.println(requestDto.getId());
		TokenResponseDto responseDto = tokenService.토큰재발급(requestDto, request);
		return responseService.getSingleResult(responseDto);
	}
	
	
}
