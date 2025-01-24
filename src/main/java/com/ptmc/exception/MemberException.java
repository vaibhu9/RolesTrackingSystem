package com.ptmc.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{
    private final String exceptionMessage;
    private final Integer statusCode;
    private final HttpStatus status;
    private final String workFlow;

    public MemberException(String exceptionMessage, Integer statusCode, HttpStatus status,
            String workFlow) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
        this.statusCode = statusCode;
        this.status = status;
        this.workFlow = workFlow;
    }

    
}
