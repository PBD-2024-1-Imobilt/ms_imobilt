package com.pbd.ms_imobilt.lote.exception;

import com.pbd.ms_imobilt.exception.ExceptionDefault;
import org.springframework.http.HttpStatus;

public class LoteClientFailedDeleteException extends ExceptionDefault {
    public LoteClientFailedDeleteException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
