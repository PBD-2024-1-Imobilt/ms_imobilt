package com.pbd.ms_imobilt.exception;

import org.springframework.http.HttpStatus;

public class LoteCiientCancelException extends LoteException{
    public LoteCiientCancelException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
