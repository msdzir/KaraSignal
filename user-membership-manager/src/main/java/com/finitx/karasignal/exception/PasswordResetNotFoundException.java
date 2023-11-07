package com.finitx.karasignal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PasswordResetNotFoundException extends RuntimeException {

    public PasswordResetNotFoundException(String message) {
        super(message);
    }
}