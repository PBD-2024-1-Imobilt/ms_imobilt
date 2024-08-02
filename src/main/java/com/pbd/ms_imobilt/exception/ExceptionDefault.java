package com.pbd.ms_imobilt.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class ExceptionDefault extends RuntimeException{
    private HttpStatus httpStatus;

    public ExceptionDefault(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
