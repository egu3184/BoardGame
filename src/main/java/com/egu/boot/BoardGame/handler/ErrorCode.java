package com.egu.boot.BoardGame.handler;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ErrorCode {
	UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류", 500),
    BRANCH_NOT_FOUND(HttpStatus.NOT_FOUND, "지점을 찾을 수 없음", 404),
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "계정이 존재하지 않거나 이메일 또는 비밀번호가 정확하지 않습니다.", 404),
	THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "테마를 찾을 수 없음",404),
	RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약내역을 찾을 수 없음",404),
	SLOT_ALEADY_RESERVED(HttpStatus.NOT_ACCEPTABLE ,"이미 예약이 된 슬롯입니다.", 406),
	SLOT_NOT_FOUND(HttpStatus.NOT_FOUND, "슬롯을 찾을 수 없음", 404),
	SLOT_FORBIDDEN(HttpStatus.FORBIDDEN, "접근할 수 없는 슬롯입니다.", 403),
	ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "계좌를 찾을 수 없음", 404),
	PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제정보를 찾을 수 없음." , 404)
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
