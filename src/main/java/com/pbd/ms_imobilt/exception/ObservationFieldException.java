package com.pbd.ms_imobilt.exception;

import org.springframework.http.HttpStatus;


public class ObservationFieldException extends LoteException{
    public ObservationFieldException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}