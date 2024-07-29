package com.pbd.ms_imobilt.exception;

import com.pbd.ms_imobilt.configuration.ExceptionConfigs;

import jakarta.validation.ValidationException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private ExceptionConfigs exceptionConfigs;

    @SuppressWarnings("null")
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status);
        problemDetail.setTitle("One or more fields are invalid.");
        problemDetail.setType(URI.create("http://localhost:8081/api/v1/error/invalid-fields"));

        var fields = ex.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(error -> ((FieldError)error).getField(), DefaultMessageSourceResolvable::getArguments));

        problemDetail.setProperty("fields", fields);
        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }



    @ExceptionHandler(HttpClientErrorException.class)
    public ProblemDetail invalidTokenException(){
        return exceptionConfigs.problemDetailConfig(HttpStatus.UNAUTHORIZED,
                "Authorization denied: Invalid token",
                "http://localhost:8081/api/v1"
                );
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ProblemDetail clientNotFoundException(){
        return  exceptionConfigs.problemDetailConfig(HttpStatus.BAD_REQUEST,
                "Client not found!",
                "http://localhost:8081/api/v1/client");
    }

    @ExceptionHandler(DuplicateLoteClientException.class)
    public ProblemDetail duplicateLoteClientException(DuplicateLoteClientException e){
        return exceptionConfigs.problemDetailConfig(HttpStatus.BAD_REQUEST,
               e.getMessage(), "http://localhost:8081/api/v1/lote");
    }

    @ExceptionHandler(LoteException.class)
    public ProblemDetail loteException(LoteException e){
        return exceptionConfigs.problemDetailConfig(
                e.getHttpStatus(),
                e.getMessage(),
                "http://localhost:8081/api/v1/lote"
                );
    }


}
