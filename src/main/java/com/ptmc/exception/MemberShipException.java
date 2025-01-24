package com.ptmc.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MemberShipException extends RuntimeException{
    private final String message;
    private final HttpStatus status;
    private final Integer statusCode;
    private final String workFlow;


    public MemberShipException(String message, HttpStatus status, Integer statusCode,
            String workFlow) {
        super(message);
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.workFlow = workFlow;
    }

    
}
