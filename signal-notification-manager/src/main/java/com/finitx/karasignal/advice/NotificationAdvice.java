package com.finitx.karasignal.advice;

import com.finitx.karasignal.constant.ErrorConstant;
import com.finitx.karasignal.exception.InvalidMailMessageException;
import com.finitx.karasignal.exception.InvalidTelegramMessageException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class NotificationAdvice {

    @ExceptionHandler(InvalidMailMessageException.class)
    public ProblemDetail handleInvalidMailMessageException(InvalidMailMessageException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

        log.error(ErrorConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(InvalidTelegramMessageException.class)
    public ProblemDetail handleInvalidTelegramMessageException(InvalidTelegramMessageException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

        log.error(ErrorConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(RuntimeException.class)
    public ProblemDetail handleException(Exception exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());

        log.error(ErrorConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }
}
