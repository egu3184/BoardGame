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
	PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제정보를 찾을 수 없음." , 404),
	
	FORBBIDDEN(HttpStatus.FORBIDDEN, "해당 리소스에 접근하기 위한 권한이 없습니다", -1002),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "보유한 권한으로 접근할 수 없는 리소스 입니다.", -1003),
	EXPIRED_TOKEN(null, "엑세스 토큰이 만료되었습니다.", -1000),
	INVALID_TOKEN(null, "잘못된 타입의 토큰입니다. ", -999),
	COMMUNICATION_ERROR(null, "통신 중 오류가 발생하였습니다", -1004)
	
	
	
	
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
