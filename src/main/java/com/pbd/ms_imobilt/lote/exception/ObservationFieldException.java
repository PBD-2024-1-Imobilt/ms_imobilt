package com.pbd.ms_imobilt.lote.exception;

import com.pbd.ms_imobilt.exception.ExceptionDefault;
import org.springframework.http.HttpStatus;


public class ObservationFieldException extends ExceptionDefault {
    public ObservationFieldException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
