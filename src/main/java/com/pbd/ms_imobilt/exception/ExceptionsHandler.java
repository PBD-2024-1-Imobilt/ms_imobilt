package com.pbd.ms_imobilt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    private ProblemDetail problemDetailConfig(HttpStatus httpStatus, String error, String uri){
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(error);
        problemDetail.setType(
                URI.create(uri)
        );
        return problemDetail;
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ProblemDetail invalidTokenException(){
        return problemDetailConfig(HttpStatus.UNAUTHORIZED,
                "Authorization denied: Invalid token",
                "http://localhost:8081/api/v1/lotes"
                );
    }

//    @ExceptionHandler(NullPointerException.class)
//    public ProblemDetail blankErrorException(NullPointerException e){
//        return problemDetailConfig(HttpStatus.INTERNAL_SERVER_ERROR,
//                e.getMessage(),
//                "http://localhost:8081/api/v1/lotes" );
//    }
}
