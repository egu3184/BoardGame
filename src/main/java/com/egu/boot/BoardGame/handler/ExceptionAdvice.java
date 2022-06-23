package com.egu.boot.BoardGame.handler;

import javax.el.MethodNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.egu.boot.BoardGame.model.api.CommonResult;
import com.egu.boot.BoardGame.service.api.ResponseService;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
public class ExceptionAdvice {

	@Autowired
    ResponseService responseService;

	
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
//        return responseService.getFailResult(ErrorCode.UNKNOWN.getCode(), ErrorCode.UNKNOWN.getMessage());
//    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected CommonResult  handleHttpMessageNotReadableException(HttpMessageNotReadableException  e) {
        return responseService.getFailResult(ErrorCode.REQUEST_BODY_MISSING.getCode(), ErrorCode.REQUEST_BODY_MISSING.getMessage());
    }
    
    @ExceptionHandler(org.springframework.web.bind.MissingServletRequestParameterException.class)
    protected CommonResult  MissingServletRequestParameterException(org.springframework.web.bind.MissingServletRequestParameterException  e) {
        return responseService.getFailResult(ErrorCode.REQUEST_PARAM_MISSING.getCode(), ErrorCode.REQUEST_PARAM_MISSING.getMessage());
    }
	
    @ExceptionHandler(CustomException.class)
    protected CommonResult handleException(CustomException e) {
        return responseService.getFailResult(e.getErrorCode().getCode(), e.getErrorCode().getMessage());
    }

 
}
