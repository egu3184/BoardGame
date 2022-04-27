package com.egu.boot.BoardGame.service;

import java.time.LocalDateTime;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.KakaoDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserResponseDto;
import com.egu.boot.BoardGame.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KakaoService {
	
	@Value("${social.kakao.url.profile}")
	private String kakaoProfileUrl;
	
//	@Value("${social.kakao.key}")
//	private String kakaoKey;
	
//	@Value("${social.kakao.redirect}")
//	private String kakaoRedirect;
	
//	@Value("${social.kakao.url.login}")
//	private String kakaoLoginUrl;
	
//	@Value("${social.kakao.url.token}")
//	private String kakaoToken;
	
//	@Value("${social.kakao.client_secret}")
//	private String kakaoSecret;
	
	private final UserRepository userRepository;
	private final WebClient webClient;
	private final JwtTokenProvider jwtTokenProvider;
	
//	//소셜 로그인 요청 Url 조립
//	public String makeLoginUrl() {
//		StringBuilder socialLoginUrl = new StringBuilder()
//				.append(kakaoLoginUrl)
//				.append("?client_id=").append(kakaoKey)
//				.append("&response_type=code")
//				.append("&redirect_uri=").append(kakaoRedirect);
////		getAuthorizationCode(socialLoginUrl.toString());
//		return socialLoginUrl.toString();
//	}

	//인가 코드로 토큰 요청
//	@Transactional
//	public String getToken(String code) {
//		//api에서 form 데이터를 원하므로 MultiValueMap으로 request 준비
//		MultiValueMap<String, String> body 
//						= new LinkedMultiValueMap<>();
//		body.add("grant_type", "authorization_code");
//		body.add("client_id", kakaoKey);
//		body.add("redirect_uri", "http://localhost:2030/social/login/kakao");
//		body.add("code", code);
//		body.add("client_secret", kakaoSecret);
//		
//		try {
//			JSONObject response = 
//					webClient
//						.post()
//						.uri(kakaoToken)
//						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
//						.accept(MediaType.APPLICATION_JSON)
//						.bodyValue(body)
//						.retrieve()												 //response body를 가져오는 메서드
//						.bodyToMono(JSONObject.class)		//body를 지정 클래스로 변환하는 메서드
//						.block();	//동기식으로 사용할 때 사용 o
//										//비동기식을 사용하여 mono나 flux를 쓸 땐 사용 x
//			
//			return (String) response.get("access_token");
//
//		} catch (Exception e) {
//			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
//		}
//	}
	
	//엑세스 토큰으로 사용자 정보 가져오기
	@Transactional
	public KakaoDto getKakaoProfile(String accessToken) {
		try {
			KakaoDto userProfile = 
					webClient
						.get()
						.uri(kakaoProfileUrl)
						.header("Authorization", "Bearer "+accessToken)
						.retrieve()
						.bodyToMono(KakaoDto.class)
						.block();
			return userProfile;
		}catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
		}
	}

	//로그인 처리
	@Transactional
	public UserResponseDto kakaoLogin(KakaoDto userProfile) {
		//가입 여부 체크
	    User user =  userRepository.findByUserIdAndProvider(String.valueOf(userProfile.getId()), "Kakao").orElse(null);
		//없으면 가입 
		if(user == null) {
			try {
				System.out.println((String.valueOf(userProfile.getId())));
				System.out.println(userProfile.getProperties().getNickname());
				user = socialSignUp(String.valueOf(userProfile.getId()), "Kakao" ,userProfile.getProperties().getNickname());
			} catch (Exception e) {
				e.printStackTrace();
//				throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
			}
		}
	    //가입했든 기존 유저든 토큰 응답
		return new UserResponseDto(
			jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), user.getRoles()), 
			jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()))
		);
	}
	
	//소셜 가입
	@Transactional
	public User socialSignUp(String id, String provider, String nickname ) {
		try {
			User user = User.builder()
					.userId(id)
					.username(nickname)
					.provider(provider)
					.createDate(LocalDateTime.now())
					.roles(Collections.singletonList("ROLE_USER"))
					.build();
			return userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR); 
		}
		
		
	}
	
	
}
