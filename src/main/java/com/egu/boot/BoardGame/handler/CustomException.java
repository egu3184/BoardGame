package com.egu.boot.BoardGame.handler;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getException());
        this.errorCode = errorCode;
    }

    public CustomException() {
        super();
        this.errorCode = ErrorCode.UNKNOWN;
    }
}

