package com.finitx.karasignal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IllegalVerificationException extends RuntimeException {

    public IllegalVerificationException(String message) {
        super(message);
    }
}
