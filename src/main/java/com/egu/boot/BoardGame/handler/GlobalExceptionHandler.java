package com.egu.boot.BoardGame.handler;

import javax.el.MethodNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
//@RestControllerAdvice("com.egu.boot.BoardGame.controller")
public class GlobalExceptionHandler {

	/*
	 * @ExceptionHandler(Exception.class) public String nullTest(Exception e) {
	 * System.err.println(e.getClass()); return "예외 처리 테스트 : 글로벌"; }
	 */

	
	// 405
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorResponse> error405(HttpRequestMethodNotSupportedException e) {
		log.error("HttpRequestMethodNotSupportedException : { }", e.getMessage());

		return ResponseEntity.status(ErrorCode.METHOD_NOT_ALLOWED.getStatus().value())
				.body(new ErrorResponse(ErrorCode.METHOD_NOT_ALLOWED));
	}

	// 500
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> error500(Exception e) {
		log.error("handlerException : { }", e.getMessage());

		return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus().value())
				.body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR));
	}

}
