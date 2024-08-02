package com.pbd.ms_imobilt.lote.exception;

import com.pbd.ms_imobilt.exception.ExceptionDefault;
import org.springframework.http.HttpStatus;

public class LoteNotFoundException extends ExceptionDefault {
    public LoteNotFoundException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
