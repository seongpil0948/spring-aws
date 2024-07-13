package com.sixplus.server.api.core.exception;

public class CustomErrorException extends RuntimeException {
    private int code = 888;
    private String message = "오류가 발생하였습니다.";


    public CustomErrorException(String message) {
        super(message);
        this.message = message;
    }

    public CustomErrorException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() { return this.message; }
}
