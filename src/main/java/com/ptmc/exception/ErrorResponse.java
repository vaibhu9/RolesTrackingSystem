package com.ptmc.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

    private Integer statusCode;
    private HttpStatus status;
    private String message;

    public ErrorResponse(Integer statusCode, HttpStatus status, String message) {
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(int statusCode2, String errorMessage) {
        //TODO Auto-generated constructor stub
    }

}
