package com.sixplus.server.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {
    private String code;
    private String message;

    public ErrorResponse() {
    }

    public static ErrorResponse of(int code, String message) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(String.valueOf(code));
        response.setMessage(message);
        return response;
    }

    public static ErrorResponse of(String code, String message) {
        ErrorResponse response = new ErrorResponse();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }
}
