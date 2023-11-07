package com.finitx.karasignal.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
        String message = exception.getMessage()
                .split("\"detail\"")[1]
                .split(",")[0]
                .replaceAll(":", "")
                .replaceAll("\"", "").trim();

        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, message);

        log.error("Caught new error: {}", problem.getDetail());
        return problem;
    }
}
