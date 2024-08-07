package com.pbd.ms_imobilt.lote.exception;

import org.springframework.http.HttpStatus;

public class ReserveException extends LoteException{
    public ReserveException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
