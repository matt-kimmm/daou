package com.daou.organizations.config;

public class DefinedException extends RuntimeException{

    public final static int BAD_REQUEST = 400;
    public final static int INTERNAL_SERVER_ERROR = 500;

    private final int code;

    public int getCode() { return code; }

    public DefinedException(int code) { this.code = code; }

    public DefinedException(int code, String message) {
        super(message);
        this.code = code;
    }

}