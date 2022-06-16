package com.egu.boot.BoardGame.handler;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public enum ErrorCode {
	
	//서버 관련 
	UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류", 500),
	REQUEST_BODY_MISSING(null ,"RequestBody가 누락되었습니다.", 400),
	REQUEST_PARAM_MISSING(null ,"RequestParameter가 누락되었습니다.", 400),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.", 400 ),
	
	//회원 관련 - 1100
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "계정이 존재하지 않거나 이메일 또는 비밀번호가 정확하지 않습니다.", 1100),
	USER_DISABLED(null, "탈퇴하거나 이용이 정지된 계정입니다.", 1101),
	USERINFO_ALREADY_USED(null, "이미 사용 중인 회원 정보를 포함하고 있습니다.", 1102),
	USERINFO_NOT_ENOUGH(null, "회원가입 필요 조건에 충족하지 않습니다.", 1103),
	INVALID_PASSWORD(HttpStatus.NOT_FOUND, "비밀번호가 일치하지 않습니다", 1104), //?
	USERINFO_CHANGE_FAILED(null, "회원정보 변경에 실패하였습니다.", 1105),
	REQUEST_RELOGIN(null, "다시 로그인하십시오.", 1106),
	
	//지점 관련
	BRANCH_NOT_FOUND(HttpStatus.NOT_FOUND, "지점을 찾을 수 없음", 404),
	ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "계좌를 찾을 수 없음", 404),
	
    //테마 관련
	THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "테마를 찾을 수 없음",404),
	
	//예약 관련
	RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약내역을 찾을 수 없습니다.",404),
	RESERVATION_UPDATE_FAIL(HttpStatus.NOT_FOUND, "예약 수정에 실패하였습니다.",404),
	
	//슬롯 관련
	SLOT_ALEADY_RESERVED(HttpStatus.NOT_ACCEPTABLE ,"이미 예약이 된 슬롯입니다.", 406),
	SLOT_NOT_FOUND(HttpStatus.NOT_FOUND, "슬롯을 찾을 수 없음", 404),
	SLOT_FORBIDDEN(HttpStatus.FORBIDDEN, "접근할 수 없는 슬롯입니다.", 403),
	PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "결제정보를 찾을 수 없음." , 404),
	
	//보안 관련 - 1000번대
	EXPIRED_REFRESH_TOKEN(null, "Refresh Token이 만료되었습니다.", -1003),
	FORBBIDDEN(HttpStatus.FORBIDDEN, "해당 리소스에 접근하기 위한 권한이 없습니다", -1002),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, "보유한 권한으로 접근할 수 없는 리소스 입니다.", -1003),
	EXPIRED_TOKEN(null, "Access Token이 만료되었습니다.", -1000),
	INVALID_TOKEN(null, "잘못된 타입의 토큰입니다. ", -999),
	
	//소셜 로그인 관련
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
