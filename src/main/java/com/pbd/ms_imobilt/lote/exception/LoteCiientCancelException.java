package com.pbd.ms_imobilt.lote.exception;

import org.springframework.http.HttpStatus;

public class LoteCiientCancelException extends LoteException {
    public LoteCiientCancelException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
