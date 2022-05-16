package com.egu.boot.BoardGame.controller.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.model.api.SingleResult;
import com.egu.boot.BoardGame.model.dto.KakaoDto;
import com.egu.boot.BoardGame.model.dto.NaverDto;
import com.egu.boot.BoardGame.model.dto.TokenDto.TokenRequestDto;
import com.egu.boot.BoardGame.model.dto.TokenDto.TokenResponseDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserResponseDto;
import com.egu.boot.BoardGame.service.SocialLoginService;
import com.egu.boot.BoardGame.service.UserService;
import com.egu.boot.BoardGame.service.api.ResponseService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class SocialLoginApiController {
	
	private final ResponseService responseService;
	private final SocialLoginService socialLoginService;

	@PostMapping("/social/login/{social}")
	public SingleResult<UserResponseDto> dd(
			@RequestBody TokenRequestDto dto, @PathVariable String social ){
		System.out.println(dto.getAccessToken());
		System.out.println(social);
		//응답 객체 초기화
		UserResponseDto userResponseDto = null;
		//소셜별 처리
		if(social.equals("kakao")) {
			//카카오
			KakaoDto userProfile = socialLoginService.getKakaoProfile(dto.getAccessToken());
			userResponseDto = socialLoginService.kakaoLogin(userProfile);
		}else if(social.equals("naver")) {
			//네이버
			NaverDto userProfile = socialLoginService.getNaverProfile(dto.getAccessToken());
			userResponseDto = socialLoginService.naverLogin(userProfile);
		}else if(social.equals("google")) {
			userResponseDto = socialLoginService.googleLogin(dto.getAccessToken());
		}
		return responseService.getSingleResult(userResponseDto);
	}
	
	
	
}
