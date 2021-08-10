package com.daou.organizations.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestControllerAdvice
public class DefinedExceptionAdvice {

    @ExceptionHandler({HttpClientErrorException.BadRequest.class, HttpServerErrorException.InternalServerError.class})
    public ResponseEntity<ErrorResult> handleException(Exception e) {
        ErrorResult response = new ErrorResult();
        if(e instanceof HttpClientErrorException.BadRequest) {
            response.setCode(400);
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessage(ErrorCode.BAD_REQUEST.getMessage());
        } else if (e instanceof HttpServerErrorException.InternalServerError) {
            response.setCode(500);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage(ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
