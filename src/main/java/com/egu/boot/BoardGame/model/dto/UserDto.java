package com.egu.boot.BoardGame.model.dto;

import com.egu.boot.BoardGame.model.User;

import lombok.Data;

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
		private String userId;
		private String username;
		//추후 추가
		
		public UserResponseDto(User user){
			this.userId = user.getUserId();
			this.username = user.getUsername();
		}
	}
	
}
