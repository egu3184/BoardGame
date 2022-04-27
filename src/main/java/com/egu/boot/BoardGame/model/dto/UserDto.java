package com.egu.boot.BoardGame.model.dto;

import com.egu.boot.BoardGame.model.User;

import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDto {

	@Data
	public static class UserRequestDto{
		private String userId;
		private String password;
		private String username;
		private String phoneNumber;
		
	}
	
	@Data
	
	public static class UserResponseDto{
//		private String userId;
//		private String username;
		
		private String accessToken;
		private String refreshToken;
		//추후 추가
		
		
		public UserResponseDto(String accessToken, String refreshToken) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
		}
		
//		public UserResponseDto(User user){
//			this.userId = user.getUserId();
//			this.username = user.getUsername();
//		}
	}
	
}
