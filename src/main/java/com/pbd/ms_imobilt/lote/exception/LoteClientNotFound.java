package com.pbd.ms_imobilt.lote.exception;

import com.pbd.ms_imobilt.exception.ExceptionDefault;
import org.springframework.http.HttpStatus;

public class LoteClientNotFound extends ExceptionDefault {
    public LoteClientNotFound(String message, HttpStatus httpStatus) {
        super(message,httpStatus);
    }
}
