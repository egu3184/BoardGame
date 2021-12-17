package com.egu.boot.BoardGame.handler;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ErrorResponse {

	private LocalDateTime timestamp = LocalDateTime.now();
	private int status;
	private String error;
	private String code;
	private String message;
	
	ErrorResponse(ErrorCode errorCode){
		this.status = errorCode.getStatus().value();
		this.error = errorCode.getStatus().name();
		this.code = errorCode.name();
		this.message = errorCode.getMessage();
	}
}
