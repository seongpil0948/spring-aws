package com.sixplus.server.api.core.exception;

public class ConnectionTimeoutException extends RuntimeException {
    private int code = 800;
    private String message = "네트워크 오류가 발생하였습니다.";


    public ConnectionTimeoutException(String message) {
        super(message);
        this.message = message;
    }

    public ConnectionTimeoutException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() { return this.message; }
}
