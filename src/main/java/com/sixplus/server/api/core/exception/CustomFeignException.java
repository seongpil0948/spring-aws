package com.sixplus.server.api.core.exception;

public class CustomFeignException extends RuntimeException {
    private int code = 610;
    private String message = "Feign Client 오류가 발생하였습니다.";


    public CustomFeignException(String message) {
        super(message);
        this.message = message;
    }

    public CustomFeignException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() { return this.message; }
}
