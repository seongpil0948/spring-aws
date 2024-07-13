package com.sixplus.server.api.core.exception;

import org.springframework.http.HttpStatus;

public class UnAutholizedException extends RuntimeException {
    private int code = HttpStatus.UNAUTHORIZED.value();
    private String message = "로그인하지 않은 사용자 또는 권한 없는 사용자 처리";


    public UnAutholizedException(String message) {
        super(message);
        this.message = message;
    }

    public UnAutholizedException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() { return this.message; }
}