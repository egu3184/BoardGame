package com.egu.boot.BoardGame.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
public class KakaoDto {
	private Long id;
	private KakaoAccount kakao_account;
	private Properties properties;
	
	@Getter
	@Setter
	public static class KakaoAccount{
		private String email;
	}
	
	@Getter
	@Setter
	public static class Properties{
		private String nickname;
	}
	
}
