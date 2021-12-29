package com.egu.boot.BoardGame.handler;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ErrorCode {
	UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "unKnown", 500),

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "user_Not_Found", 404),
	THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "theme_Not_Found",404),
	RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "reservation_Not_Found",404),
	SLOT_NOT_FOUND(HttpStatus.NOT_FOUND, "slot_Not_Found", 404),
	;
	
	private HttpStatus status;
	private String exception;
	private int code;

	ErrorCode(HttpStatus status, String exception, int code) {
		this.status = status;
		this.exception = exception;
		this.code = code;
	}
}
