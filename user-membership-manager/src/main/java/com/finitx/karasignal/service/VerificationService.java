package com.finitx.karasignal.service;

import com.finitx.karasignal.constant.ErrorConstant;
import com.finitx.karasignal.constant.LogConstant;
import com.finitx.karasignal.exception.IllegalVerificationException;
import com.finitx.karasignal.exception.VerificationNotFoundException;
import com.finitx.karasignal.model.User;
import com.finitx.karasignal.model.Verification;
import com.finitx.karasignal.repository.VerificationRepository;
import com.finitx.karasignal.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

/**
 * This class is responsible for verifying user's email.
 *
 * @author Amin Norouzi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationService {

    private final VerificationRepository verificationRepository;
    private final UserService userService;
    private final DateTimeUtil dateTimeUtil;

    @Value("${verification.token.expiration}")
    private Integer expiration; // 24 hours

    /**
     * Gets a user and creates a verification with the given info and returns it.
     *
     * @param user
     * @return Verification
     */
    public Verification create(User user) {
        Date expirationDate = dateTimeUtil.expiresAt(expiration);
        String generatedToken = UUID.randomUUID().toString();

        Verification request = Verification.builder()
                .token(generatedToken)
                .user(user)
                .expiresAt(expirationDate)
                .build();

        Verification saved = verificationRepository.save(request);

        log.info(LogConstant.VERIFICATION_CREATED, saved);
        return saved;
    }

    /**
     * Returns a verification object with give id and if it doesn't exist in the database, throws a VerificationNotFoundException
     * exception.
     *
     * @param token
     * @return Verification
     */
    public Verification getByToken(String token) {
        Verification found = verificationRepository.findByToken(token)
                .orElseThrow(() -> new VerificationNotFoundException(ErrorConstant.VERIFICATION_NOT_FOUND));

        log.info(LogConstant.VERIFICATION_FOUND, found);
        return found;
    }

    /**
     * Gets a verification token and checks if it's valid. If true. will update the verification as confirmed and the
     * user as enabled.
     *
     * @param token
     */
//    @Transactional
    public void confirm(String token) {
        Verification verification = getByToken(token);

        validate(verification);

        User user = verification.getUser();
        userService.enable(user);

        verification.setConfirmedAt(dateTimeUtil.getNow());
        verificationRepository.save(verification);

        log.info(LogConstant.VERIFICATION_CONFIRMED, verification);
    }

    /**
     * Gets a verification and checks if it's valid. It may throw IllegalVerificationException exception.
     *
     * @param verification
     */
    public void validate(Verification verification) {
        if (dateTimeUtil.isExpired(verification.getExpiresAt())) {
            throw new IllegalVerificationException(ErrorConstant.VERIFICATION_EXPIRED);
        }

        if (verification.getConfirmedAt() != null) {
            throw new IllegalVerificationException(ErrorConstant.VERIFICATION_CONFIRMED);
        }
    }
}
