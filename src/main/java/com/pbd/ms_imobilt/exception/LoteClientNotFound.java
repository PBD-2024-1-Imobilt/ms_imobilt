package com.pbd.ms_imobilt.exception;

import org.springframework.http.HttpStatus;

public class LoteClientNotFound extends LoteException{
    public LoteClientNotFound(String message, HttpStatus httpStatus) {
        super(message,httpStatus);
    }
}
