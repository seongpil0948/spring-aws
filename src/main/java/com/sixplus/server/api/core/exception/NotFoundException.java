package com.sixplus.server.api.core.exception;

public class NotFoundException extends RuntimeException {
    private int code = 404;
    private String message = "해당 API는 찾을 수 없습니다.";


    public NotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public NotFoundException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() { return this.message; }
}
