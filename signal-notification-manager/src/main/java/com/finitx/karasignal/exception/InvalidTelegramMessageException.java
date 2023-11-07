package com.finitx.karasignal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTelegramMessageException extends RuntimeException {

    public InvalidTelegramMessageException(String message) {
        super(message);
    }
}
