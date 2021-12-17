package com.egu.boot.BoardGame.handler;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestHandler {

	@GetMapping("/controllerException")
	public void controllerException() {
		throw new NullPointerException();
	}

	/*
	//위 Exception이 났을 때 얘가 받아줄 거임.
	//컨트롤러 내에서 구현하면 글로벌보다 우선 순위가 높네.
	@ExceptionHandler(NullPointerException.class)
	public String nullTest(Exception e) {
		System.err.println(e.getClass());
		return "예외 처리 테스트";
	}
	
	*/
}
