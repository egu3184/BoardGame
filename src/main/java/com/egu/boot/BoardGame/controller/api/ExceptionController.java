package com.egu.boot.BoardGame.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egu.boot.BoardGame.handler.CustomException;
import com.egu.boot.BoardGame.handler.ErrorCode;
import com.egu.boot.BoardGame.model.api.CommonResult;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/exception")
@RequiredArgsConstructor
public class ExceptionController {

	
	@GetMapping("/entrypoint")
	public CommonResult entrypointException() {
		throw new CustomException(ErrorCode.TOKEN_NOT_VALID);
	}
	
	@GetMapping("/accessdenied")
	public CommonResult accessDeniedException() {
		throw new CustomException(ErrorCode.FORBBIDDEN);
	}
	
}
