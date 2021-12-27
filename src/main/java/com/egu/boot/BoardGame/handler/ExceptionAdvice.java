package com.egu.boot.BoardGame.handler;

import java.net.http.HttpRequest;

import javax.el.MethodNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@Autowired
    MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected CommonResult defaultException(HttpServletRequest request, Exception e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
    }

    @ExceptionHandler(CustomUserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult userNotFoundException(HttpServletRequest request, CustomUserNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
    }
    
    @ExceptionHandler(CustomThemeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult themeNotFoundException(HttpServletRequest request, CustomThemeNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("themeNotFound.code")), getMessage("themeNotFound.msg"));
    }
    
    @ExceptionHandler(CustomSlotNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult slotNotFoundException(HttpServletRequest request, CustomSlotNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("slotNotFound.code")), getMessage("slotNotFound.msg"));
    }
    
    @ExceptionHandler(CustomReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected CommonResult reservationNotFoundException(HttpServletRequest request, CustomReservationNotFoundException e) {
        return responseService.getFailResult(Integer.valueOf(getMessage("reservationNotFound.code")), getMessage("reservationNotFound.msg"));
    }

    // code정보에 해당하는 메시지를 조회합니다.
    private String getMessage(String code) {
        return getMessage(code, null);
    }
    // code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
    private String getMessage(String code, Object[] args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }
}