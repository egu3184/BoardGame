package com.egu.boot.BoardGame.handler;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ErrorCode {
	UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류", 500),

	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없음", 404),
	THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "테마를 찾을 수 없음",404),
	RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약내역을 찾을 수 없음",404),
	SLOT_NOT_FOUND(HttpStatus.NOT_FOUND, "슬롯을 찾을 수 없음", 404),
	;
	
	private HttpStatus status;
	private String message;
	private int code;

	ErrorCode(HttpStatus status, String message, int code) {
		this.status = status;
		this.message = message;
		this.code = code;
	}
}
