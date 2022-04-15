package com.egu.boot.BoardGame.model.dto;

import com.egu.boot.BoardGame.model.User;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class TokenDto {

	@Data
	public static class TokenRequestDto{
		private int Id;
		private String accessToken;
		private String refreshToken;
		
	}
	
	@Getter
	public static class TokenResponseDto{
		private String accessToken;
		private String refreshToken;
		private String result;
	
		public TokenResponseDto(String accessToken, String refreshToken, String result) {
			this.accessToken = accessToken;
			this.refreshToken = refreshToken;
			this.result = result;
		}
	}
	
	
}
