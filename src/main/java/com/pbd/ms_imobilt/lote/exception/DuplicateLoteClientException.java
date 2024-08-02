package com.pbd.ms_imobilt.lote.exception;

import org.springframework.http.HttpStatus;

public class DuplicateLoteClientException extends LoteException{

    public DuplicateLoteClientException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
