package com.sixplus.server.api.core.exception;

public class BadRequestException extends RuntimeException {
    private int code = 400;
    private String message = "해당 API는 잘못된 요청 입니다.";


    public BadRequestException(String message) {
        super(message);
        this.message = message;
    }

    public BadRequestException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() { return this.message; }
}
