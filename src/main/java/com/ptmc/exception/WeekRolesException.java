package com.ptmc.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WeekRolesException extends RuntimeException{
    private String message;
    private Integer statusCode;
    private HttpStatus status;
    private String workFlow;

    public WeekRolesException(String message,Integer statusCode, HttpStatus status, String workFlow) {
        super(message);
        this.message = message;
        this.statusCode = statusCode;
        this.status = status;
        this.workFlow = workFlow;
    }

    
}
