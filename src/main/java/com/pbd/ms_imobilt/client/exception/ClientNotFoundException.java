package com.pbd.ms_imobilt.client.exception;

import org.springframework.http.HttpStatus;

public class ClientNotFoundException extends ClientException {


    public ClientNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
