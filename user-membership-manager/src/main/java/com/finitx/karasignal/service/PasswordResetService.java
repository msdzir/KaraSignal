package com.finitx.karasignal.service;

import com.finitx.karasignal.constant.ErrorConstant;
import com.finitx.karasignal.constant.LogConstant;
import com.finitx.karasignal.dto.PasswordResetRequest;
import com.finitx.karasignal.exception.IllegalPasswordResetException;
import com.finitx.karasignal.exception.PasswordResetNotFoundException;
import com.finitx.karasignal.model.PasswordReset;
import com.finitx.karasignal.model.User;
import com.finitx.karasignal.repository.PasswordResetRepository;
import com.finitx.karasignal.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final PasswordResetRepository passwordResetRepository;
    private final UserService userService;
    private final DateTimeUtil dateTimeUtil;

    @Value("${password-reset.token.expiration}")
    private Integer expiration; // 24 hours

    /**
     * Gets a user and creates a PasswordReset with the given info and returns it.
     *
     * @param user
     * @return PasswordReset
     */
    public PasswordReset create(User user) {
        Date expirationDate = dateTimeUtil.expiresAt(expiration);
        String generatedToken = UUID.randomUUID().toString();

        PasswordReset request = PasswordReset.builder()
                .token(generatedToken)
                .user(user)
                .expiresAt(expirationDate)
                .build();

        PasswordReset saved = passwordResetRepository.save(request);

        log.info(LogConstant.PASSWORD_RESET_CREATED, saved);
        return saved;
    }

    /**
     * Returns a PasswordReset object with give id and if it doesn't exist in the database, throws a PasswordResetNotFoundException
     * exception.
     *
     * @param token
     * @return PasswordReset
     */
    public PasswordReset getByToken(String token) {
        PasswordReset found = passwordResetRepository.findByToken(token)
                .orElseThrow(() -> new PasswordResetNotFoundException("Password reset token was not found!"));

        log.info(LogConstant.PASSWORD_RESET_FOUND, found);
        return found;
    }

    /**
     * Gets a password reset request and checks if it's valid. if successful, it will update the user password and
     * return it back.
     *
     * @param request
     */
    @Transactional
    public PasswordReset reset(PasswordResetRequest request) {
        PasswordReset reset = getByToken(request.getToken());
        validate(request, reset);

        User user = reset.getUser();
        userService.reset(user, request.getPassword());

        reset.setUsedAt(dateTimeUtil.getNow());
        passwordResetRepository.save(reset);

        log.info(LogConstant.PASSWORD_RESET_DONE, reset);
        return reset;
    }

    /**
     * Gets a password reset and checks if it's valid. It may throw IllegalPasswordResetException exception.
     *
     * @param request
     * @param reset
     */
    public void validate(PasswordResetRequest request, PasswordReset reset) {
        if (dateTimeUtil.isExpired(reset.getExpiresAt())) {
            throw new IllegalPasswordResetException(ErrorConstant.PASSWORD_RESET_EXPIRED);
        }

        if (reset.getUsedAt() != null) {
            throw new IllegalPasswordResetException(ErrorConstant.PASSWORD_RESET_USED);
        }

        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new IllegalPasswordResetException(ErrorConstant.PASSWORD_RESET_DONT_MATCH);
        }
    }
}
