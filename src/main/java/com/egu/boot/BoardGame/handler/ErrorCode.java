package com.egu.boot.BoardGame.handler;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ErrorCode {


	// 400
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
	
	//404 
	POST_NOT_FOUND(HttpStatus.NOT_FOUND, "정보를 찾을 수 없습니다."),
	
	//405
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 메서드입니다."),
	
	//500
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류 입니다");
	
	private HttpStatus status;
	
	private String message;
	
	ErrorCode(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}
	
	
	
}
