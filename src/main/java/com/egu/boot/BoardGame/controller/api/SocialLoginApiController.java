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
import com.egu.boot.BoardGame.model.dto.TokenDto.TokenRequestDto;
import com.egu.boot.BoardGame.model.dto.TokenDto.TokenResponseDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserResponseDto;
import com.egu.boot.BoardGame.service.KakaoService;
import com.egu.boot.BoardGame.service.UserService;
import com.egu.boot.BoardGame.service.api.ResponseService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class SocialLoginApiController {
	
	private final ResponseService responseService;
	private final KakaoService kakaoService;
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;
	
//	//소셜 로그인 요청url 리턴
//	@GetMapping("/login/{social}/url")
//	public String getLoginUrl(@PathVariable String social) {
//		String loginUrl = null;
//		if(social.equals("kakao")) {
//			loginUrl = kakaoService.makeLoginUrl();
//			kakaoService.makeLoginUrl();
//		}
//		return loginUrl;
//	}
	
//	@GetMapping("/social/login/{social}")
//	public SingleResult<UserResponseDto> dddd(@RequestParam String code, @PathVariable String social) {
//		System.out.println(code);
//		String accessToken = null;
//		UserResponseDto userResponseDto = null;
//		if(social.equals("kakao")) {
//			accessToken = kakaoService.getToken(code);
//			KakaoDto userProfile = kakaoService.getKakaoProfile(accessToken);
//			userResponseDto = kakaoService.kakaoLogin(userProfile);
//		}
//		
//		return responseService.getSingleResult(userResponseDto);
//	}

	@PostMapping("/social/login/{social}")
	public SingleResult<UserResponseDto> dd(
			@RequestBody TokenRequestDto dto, @PathVariable String social ){
		UserResponseDto userResponseDto = null;
		if(social.equals("kakao")) {
			KakaoDto userProfile = kakaoService.getKakaoProfile(dto.getAccessToken());
			userResponseDto = kakaoService.kakaoLogin(userProfile);
		}
		
		return responseService.getSingleResult(userResponseDto);
	}
	
}
