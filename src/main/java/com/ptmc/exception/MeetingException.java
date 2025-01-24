package com.ptmc.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MeetingException extends RuntimeException{
    private final String message;
    private final HttpStatus status;
    private final Integer statusCode;
    private final String workFlow;

    public MeetingException(String message,HttpStatus status, Integer statusCode, String workFlow) {
        super(message);
        this.message = message;
        this.status = status;
        this.statusCode = statusCode;
        this.workFlow = workFlow;
    }
}
