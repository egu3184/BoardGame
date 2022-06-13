package com.egu.boot.BoardGame.model.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import com.egu.boot.BoardGame.model.Reservation;
import com.egu.boot.BoardGame.model.User;
import com.egu.boot.BoardGame.model.dto.ReservationDto.ReservationResponseDto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Transactional(readOnly = true)
public class UserDto {

	@Data
	public static class UserRequestDto{
		private String userId;
		private String password;
		private String nickname;
		private String phoneNum;
		private Boolean privacyAgree;
		private Boolean prAgree;
		private String newPassword;
	}
	
	@Data
	public static class UserResponseDto{
		private String accessToken;
		private String refreshToken;
		private String nickname;
		private String phoneNum;
		private String provider;
		private String userId;
	
		public UserResponseDto(String accessToken, String refreshToken) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
		}
		
		public UserResponseDto(User user) {
			this.nickname = user.getNickname();
			this.phoneNum =  user.getPhoneNumber();
		    this.provider = user.getProvider();
		    if(user.getProvider().equals("Application")) {
		    	this.userId = user.getUserId();
		    }		
		}
		
		public UserResponseDto(Authentication authentication) {
			User user = (User) authentication.getPrincipal();
			this.nickname = user.getNickname();
			this.phoneNum =  user.getPhoneNumber();
		    this.provider = user.getProvider();
		    if(user.getProvider().equals("Application")) {
		    	this.userId = user.getUserId();
		    }
		}
	}
	
}
