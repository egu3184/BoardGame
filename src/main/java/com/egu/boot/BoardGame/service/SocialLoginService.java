package com.egu.boot.BoardGame.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.KakaoDto;
import com.egu.boot.BoardGame.model.dto.NaverDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserResponseDto;
import com.egu.boot.BoardGame.repository.UserRepository;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SocialLoginService {
	
	@Value("${social.google.clientId}")
	private String googleClientId;
	
	@Value("${social.kakao.url.profile}")
	private String kakaoProfileUrl;
	
	@Value("${social.naver.url.profile}")
	private String  naverProfileUrl;
	
	private final UserRepository userRepository;
	private final WebClient webClient;
	private final JwtTokenProvider jwtTokenProvider;

	
	//카카오 사용자 프로필 요청
	@Transactional
	public KakaoDto getKakaoProfile(String accessToken) {
		try {
			KakaoDto kakaoUserProfile = 
					webClient
						.get()
						.uri(kakaoProfileUrl)
						.header("Authorization", "Bearer "+accessToken)
						.retrieve()
						.bodyToMono(KakaoDto.class)
						.block();
			return kakaoUserProfile;
		}catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
		}
	}

	//카카오 로그인 처리
	@Transactional
	public UserResponseDto kakaoLogin(KakaoDto userProfile) {
		//가입 여부 체크
	    User user =  userRepository.findByUserIdAndProvider(String.valueOf(userProfile.getId()), "Kakao").orElse(null);
		//없으면 가입 
		if(user == null) {
			try {
				user = socialSignUp(String.valueOf(userProfile.getId()), "Kakao" ,userProfile.getProperties().getNickname());
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
			}
		}else if(user.isEnabled() == false) { //회원 상태 체크
			throw new CustomException(ErrorCode.USER_DISABLED);
		}
		
	    //가입했든 기존 유저든 토큰 응답
		return new UserResponseDto(
			jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), user.getRoles()), 
			jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()))
		);
	}
	
	//소셜 회원가입
	@Transactional
	public User socialSignUp(String id, String provider, String nickname ) {
		try {
			User user = User.builder()
					.userId(id)
					.nickname(nickname)
					.provider(provider)
					.isEnabled(true)
					.createDate(LocalDateTime.now())
					.roles(Collections.singletonList("ROLE_USER"))
					.build();
			return userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR); 
		}
	}
	
	//구글 로그인
	@Transactional
	public UserResponseDto googleLogin(String accessToken) {	
		HttpTransport transport = Utils.getDefaultTransport();
		 JsonFactory jsonFactory = Utils.getDefaultJsonFactory();
		
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
						.Builder(transport, jsonFactory)
						.setAudience(Collections.singletonList(googleClientId))
						.build();

		GoogleIdToken idToken = null;
		try {
			System.out.println(accessToken);
			 idToken = verifier.verify(accessToken);
			 System.out.println(idToken);
		} catch (GeneralSecurityException | IOException e) {
			e.printStackTrace(); //엑세스 토큰 유효x
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
		}
		if(idToken == null) {	//토큰이 null일 때 
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
		}
		Payload payload = idToken.getPayload();
		String userId = payload.getSubject();
		String nickname = (String )payload.get("name");
		
		//여기서부터는 가입 유무에 따라 로그인 처리 or 가입 처리 후 로그인처리
		User user = userRepository.findByUserIdAndProvider(userId, "Google").orElse(null);
		if(user == null) {	//회원이 아니라면 가입
			user = socialSignUp(userId, "google", nickname);
		}else if(user.isEnabled() == false) { //회원 상태 체크
			throw new CustomException(ErrorCode.USER_DISABLED);
		}
		//토큰 발급
		//가입했든 기존 유저든 토큰 응답
		return new UserResponseDto(
					jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), user.getRoles()), 
					jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()))
				);
	}

	//네이버 사용자 프로필 요청
	@Transactional
	public NaverDto getNaverProfile(String accessToken) {
		NaverDto dto = (NaverDto)
				getSocialProfile(naverProfileUrl, accessToken, NaverDto.class);
		 return dto;
		 //나중에 다시 리팩토링 하기
	}

	//네이버 로그인
	@Transactional
	public UserResponseDto naverLogin(NaverDto naverUserProfile) {
		//가입 여부 체크
	    User user =  userRepository.findByUserIdAndProvider(naverUserProfile.getResponse().getId(), "Naver").orElse(null);
		//없으면 가입 
		if(user == null) {
			try {
				user = socialSignUp(naverUserProfile.getResponse().getId(), "Naver" ,naverUserProfile.getResponse().getNickname());
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
			}
		} else if(user.isEnabled() == false) {
			throw new CustomException(ErrorCode.USER_DISABLED);
		}
	    //가입했든 기존 유저든 토큰 응답
		return new UserResponseDto(
			jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), user.getRoles()), 
			jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()))
		);
	}
	
	
	//프로필 요청 넣는 메서드 - 추후 리팩토링시 사용
	@Transactional
	public <T> Object getSocialProfile(String profileRequestUrl,String accessToken, T dtoClass) {
		try {
			T naverUserProfile = webClient
					.get()
					.uri(profileRequestUrl)
					.header("Authorization", "Bearer "+accessToken)
					.retrieve()
					.bodyToMono((Class<T>) dtoClass)
					.block();
			return naverUserProfile;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
		}
	}
	
	
	//토큰 만들어서 리턴하는 메서드
	
	
	
	
}