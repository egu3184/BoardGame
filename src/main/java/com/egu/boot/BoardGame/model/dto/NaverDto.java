package com.egu.boot.BoardGame.model.dto;

import lombok.Data;

@Data
public class NaverDto {
	private String message;
	private Response response;
	
	@Data
	public static class Response{
		private String id;
		private String nickname;
		private String email;
	}
}
