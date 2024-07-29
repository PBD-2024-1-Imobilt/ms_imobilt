package com.pbd.ms_imobilt.exception;

import org.springframework.http.HttpStatus;

public class LoteClientFailedDeleteException extends LoteException{
    public LoteClientFailedDeleteException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
