package com.pbd.ms_imobilt.exception;

import org.springframework.http.HttpStatus;

public class LoteNotFoundException extends LoteException{
    public LoteNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
