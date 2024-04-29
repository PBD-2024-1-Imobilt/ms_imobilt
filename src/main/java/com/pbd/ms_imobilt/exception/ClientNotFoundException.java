package com.pbd.ms_imobilt.exception;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(String message) {
        super(message);
    }

    public ClientNotFoundException() {
    }
}
