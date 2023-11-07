package com.finitx.karasignal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMailMessageException extends RuntimeException {

    public InvalidMailMessageException(String message) {
        super(message);
    }
}
