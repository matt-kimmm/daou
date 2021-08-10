package com.daou.organizations.config;

public enum ErrorCode {

    BAD_REQUEST(400, "요청값이 적절하지 않음"),
    INTERNAL_SERVER_ERROR(500, "내부 서버 오류");

    private int code;
    private String message;

    public String getMessage() { return message; }

    public int getCode() { return code; }

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}