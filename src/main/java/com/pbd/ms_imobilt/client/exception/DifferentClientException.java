package com.pbd.ms_imobilt.client.exception;

import org.springframework.http.HttpStatus;

public class DifferentClientException extends ClientException {
    public DifferentClientException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
