package com.pbd.ms_imobilt.exception;

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

    private ProblemDetail problemDetailConfig(HttpStatus httpStatus, String error, String uri){
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setTitle(error);
        problemDetail.setType(
                URI.create(uri)
        );
        return problemDetail;
    }

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
        return problemDetailConfig(HttpStatus.UNAUTHORIZED,
                "Authorization denied: Invalid token",
                "http://localhost:8081/api/v1/lotes"
                );
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ProblemDetail clientNotFoundException(){
        return  problemDetailConfig(HttpStatus.BAD_REQUEST,
                "Client not found!",
                "http://localhost:8081/api/v1/client");
    }
}
