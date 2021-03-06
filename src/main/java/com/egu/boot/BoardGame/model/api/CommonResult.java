package com.egu.boot.BoardGame.model.api;

import lombok.Data;

@Data
public class CommonResult {
 
	// "응답 성공 여부 : true / false
	private boolean success;
	
	// "응답 코드 : 0이상 정상 , 0미만 실패"
	private int code;
	
	// "응답 메시지"
	private String message;
	
}
