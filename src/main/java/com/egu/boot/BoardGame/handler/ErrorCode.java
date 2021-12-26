package com.egu.boot.BoardGame.handler;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR,"unKnown"),

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "userNotFound"),
	THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "themeNotFound"),
	RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "reservationNotFound"),
	SLOT_NOT_FOUND(HttpStatus.NOT_FOUND, "slotNotFound"),
	;
	
	private HttpStatus status;

	private String exception;
	
	ErrorCode(HttpStatus status, String exception) {
		this.status = status;
		this.exception = exception;
	}
	
	
	
}
