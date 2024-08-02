package com.pbd.ms_imobilt.client.exception;

import com.pbd.ms_imobilt.exception.ExceptionDefault;
import org.springframework.http.HttpStatus;


public abstract class ClientException extends ExceptionDefault {

    public ClientException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
