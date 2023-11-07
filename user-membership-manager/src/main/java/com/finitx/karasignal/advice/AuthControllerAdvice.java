package com.finitx.karasignal.advice;

import com.finitx.karasignal.constant.ErrorConstant;
import com.finitx.karasignal.constant.LogConstant;
import com.finitx.karasignal.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This class is responsible for handling the auth exceptions and each method will handle a specific exception.
 *
 * @author Amin Norouzi
 */
@Slf4j
@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(IllegalUserRequestException.class)
    public ProblemDetail handleIllegalUserRequestException(IllegalUserRequestException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(PasswordResetNotFoundException.class)
    public ProblemDetail handlePasswordResetNotFoundException(PasswordResetNotFoundException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(IllegalPasswordResetException.class)
    public ProblemDetail handleIllegalPasswordResetException(IllegalPasswordResetException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ProblemDetail handleUsernameNotFoundException(UsernameNotFoundException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentialsException(BadCredentialsException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, ErrorConstant.BAD_CREDENTIAL);

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(DisabledException.class)
    public ProblemDetail handleDisabledException(DisabledException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.FORBIDDEN, ErrorConstant.VERIFICATION_INVALID);

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.FORBIDDEN, ErrorConstant.VERIFICATION_INVALID);

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(SignatureException.class)
    public ProblemDetail handleSignatureException(SignatureException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(UnsupportedJwtException.class)
    public ProblemDetail handleUnsupportedJwtException(UnsupportedJwtException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwtException(ExpiredJwtException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ProblemDetail handleMalformedJwtException(MalformedJwtException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(IllegalTokenRequestException.class)
    public ProblemDetail handleIllegalTokenRequestException(IllegalTokenRequestException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(IllegalVerificationException.class)
    public ProblemDetail handleIllegalVerificationException(IllegalVerificationException exception) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());

        log.error(LogConstant.ERROR_MESSAGE, problem.getDetail());
        return problem;
    }

    @ExceptionHandler(VerificationNotFoundException.class)
    public ProblemDetail handleVerificationNotFoundException(VerificationNotFoundException exception) {
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
