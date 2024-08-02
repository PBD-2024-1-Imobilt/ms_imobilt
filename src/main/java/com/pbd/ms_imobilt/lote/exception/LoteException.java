package com.pbd.ms_imobilt.lote.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class LoteException extends RuntimeException{
    private HttpStatus httpStatus;
    public LoteException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
