package com.pbd.ms_imobilt.lote.exception;

import org.springframework.http.HttpStatus;

public class SaleException extends LoteException{

    public SaleException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
