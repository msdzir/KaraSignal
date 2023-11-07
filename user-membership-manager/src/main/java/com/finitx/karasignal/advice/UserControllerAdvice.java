package com.finitx.karasignal.advice;

import com.finitx.karasignal.constant.LogConstant;
import com.finitx.karasignal.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is responsible for handling the user exceptions and each method will handle a specific exception.
 *
 * @author Amin Norouzi
 */
@Slf4j
@RestControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleUserNotFoundException(UserNotFoundException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }
}