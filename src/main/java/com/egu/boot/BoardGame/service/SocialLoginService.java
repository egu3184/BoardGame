package com.egu.boot.BoardGame.service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.egu.boot.BoardGame.config.security.JwtTokenProvider;
import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.KakaoDto;
import com.egu.boot.BoardGame.model.dto.NaverDto;
import com.egu.boot.BoardGame.model.dto.TokenDto.TokenRequestDto;
import com.egu.boot.BoardGame.model.dto.UserDto.UserResponseDto;
import com.egu.boot.BoardGame.repository.UserRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;
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
	@Value("${social.kakao.url.deactivate}")
	private String kakaoDeactivateUrl;
	
	@Value("${social.naver.url.profile}")
	private String  naverProfileUrl;
	@Value("${social.naver.clientId}")
	private String naverClientId;
	@Value("${social.naver.clientSecret}")
	private String naverClientSecret;
	@Value("${social.naver.url.deactivate}")
	private String  naverDeactivateUrl;
	
	private final UserRepository userRepository;
	private final WebClient webClient;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserService userService;


	
	//????????? ????????? ????????? ??????
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

	//????????? ????????? ??????
	@Transactional
	public UserResponseDto kakaoLogin(KakaoDto userProfile) {
		//?????? ?????? ??????
	    User user =  userRepository.findByUserIdAndProvider(String.valueOf(userProfile.getId()), "Kakao").orElse(null);
		//????????? ?????? 
		if(user == null) {
			try {
				user = socialSignUp(String.valueOf(userProfile.getId()), "Kakao" ,userProfile.getProperties().getNickname());
			} catch (Exception e) {
				e.printStackTrace();
				throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
			}
		}else if(user.isEnabled() == false) { //?????? ?????? ??????
			throw new CustomException(ErrorCode.USER_DISABLED);
		}
		
	    //???????????? ?????? ????????? ?????? ??????
		return new UserResponseDto(
			jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), user.getRoles()), 
			jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()))
		);
	}
	
	//?????? ????????????
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
	
	//?????? ?????????
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
			e.printStackTrace(); //????????? ?????? ??????x
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
		}
		if(idToken == null) {	//????????? null??? ??? 
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
		}
		Payload payload = idToken.getPayload();
		String userId = payload.getSubject();
		String nickname = (String )payload.get("name");
		
		//?????????????????? ?????? ????????? ?????? ????????? ?????? or ?????? ?????? ??? ???????????????
		User user = userRepository.findByUserIdAndProvider(userId, "Google").orElse(null);
		if(user == null) {	//????????? ???????????? ??????
			user = socialSignUp(userId, "google", nickname);
		}else if(user.isEnabled() == false) { //?????? ?????? ??????
			throw new CustomException(ErrorCode.USER_DISABLED);
		}
		//?????? ??????
		//???????????? ?????? ????????? ?????? ??????
		return new UserResponseDto(
					jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), user.getRoles()), 
					jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()))
				);
	}

	//????????? ????????? ????????? ??????
	@Transactional
	public NaverDto getNaverProfile(String accessToken) {
		NaverDto dto = (NaverDto)
				getSocialProfile(naverProfileUrl, accessToken, NaverDto.class);
		 return dto;
		 //????????? ?????? ???????????? ??????
	}

	//????????? ?????????
	@Transactional
	public UserResponseDto naverLogin(NaverDto naverUserProfile) {
		//?????? ?????? ??????
	    User user =  userRepository.findByUserIdAndProvider(naverUserProfile.getResponse().getId(), "Naver").orElse(null);
		//????????? ?????? 
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
	    //???????????? ?????? ????????? ?????? ??????
		return new UserResponseDto(
			jwtTokenProvider.createAccessToken(String.valueOf(user.getId()), user.getRoles()), 
			jwtTokenProvider.createRefreshToken(String.valueOf(user.getId()))
		);
	}
	
	
	//????????? ?????? ?????? ????????? - ?????? ??????????????? ??????
	@Transactional
	public <T> Object getSocialProfile(String profileRequestUrl,String accessToken, T dtoClass) {
		try {
			T userProfile = webClient
					.get()
					.uri(profileRequestUrl)
					.header("Authorization", "Bearer "+accessToken)
					.retrieve()
					.bodyToMono((Class<T>) dtoClass)
					.block();
			return userProfile;
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
		}
	}
	
	@Transactional
	public long ??????????????????(String social, TokenRequestDto requestDto) {
		boolean result = false;
		switch (social) {
		case "Kakao" :
			result = ?????????????????????(requestDto.getAccessToken()); 
			break;
		case "Naver":
			result = ?????????????????????(requestDto.getAccessToken()); 
			break;	
		}
		if(!result) { 
			return 0;
		}else {
			return userService.????????????(null);
		}
	}
	@Transactional
	public boolean ?????????????????????(String socialAccessToken) {
		try {
			JSONObject result = 
					webClient
						.post()
						.uri(kakaoDeactivateUrl)
						.header("Authorization", "Bearer "+socialAccessToken)
						.retrieve()
						.bodyToMono(JSONObject.class)
						.block();
			if(result.get("id") != null) { return true; }
			else { return false; }
		} catch (WebClientResponseException e) {
			throw new CustomException(ErrorCode.SOCIAL_ALREADY_DEACTIVATE);
		} catch(Exception e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
		}
	}
	@Transactional
	public boolean ?????????????????????(String socialAccessToken) {
		try {
			MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
			formData.add("client_id", naverClientId);
			formData.add("client_secret", naverClientSecret);
			formData.add("access_token", socialAccessToken);
			formData.add("grant_type", "delete");
			formData.add("service_provider","NAVER");
			
			String result = 
					webClient
						.post()
						.uri(naverDeactivateUrl)
						.body(BodyInserters.fromFormData(formData))
						.retrieve() 
						.bodyToMono(String.class)
						.block();
			JSONObject jsonResult = (JSONObject) new JSONParser().parse(result);
			if(jsonResult.get("result").equals("success")) { return true; }
			else { return false; }
		} catch (WebClientResponseException e) {
			throw new CustomException(ErrorCode.SOCIAL_ALREADY_DEACTIVATE);	
		}catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(ErrorCode.COMMUNICATION_ERROR);
		}
	}
	
	
	
	
	
	
}
