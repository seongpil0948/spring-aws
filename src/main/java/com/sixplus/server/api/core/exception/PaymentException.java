package com.sixplus.server.api.core.exception;

public class PaymentException extends RuntimeException {
    private int code = 750;
    private String message = "결제 모듈에 에러가 발생하였습니다.";


    public PaymentException(String message) {
        super(message);
        this.message = message;
    }

    public PaymentException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() { return this.message; }
}
