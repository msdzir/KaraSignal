package com.finitx.karasignal.service;

import com.finitx.karasignal.constant.LogConstant;
import com.finitx.karasignal.model.PasswordReset;
import com.finitx.karasignal.model.Verification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for sending emails to users.
 *
 * @author Amin Norouzi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${mail.service.message.verification.subject}")
    private String verificationSubject;

    @Value("${mail.service.message.verification.text}")
    private String verificationText;

    @Value("${mail.service.message.password-reset.subject}")
    private String passwordResetSubject;

    @Value("${mail.service.message.password-reset.text}")
    private String passwordResetText;

    /**
     * Generates a message and sends a verification email to the user asynchronously.
     *
     * @param verification
     */
    @Async
    public void send(Verification verification) {
        SimpleMailMessage message = generate(verification.getUser().getEmail());
        message.setSubject(verificationSubject);
        message.setText(verificationText.formatted(verification.getToken()));

//        javaMailSender.send(message);
        System.out.println(verification.getToken());
        log.info(LogConstant.EMAIL_SENT, verification.getUser(), verification);
    }

    /**
     * Generates a message and sends a password reset email to the user asynchronously.
     *
     * @param reset
     */
    @Async
    public void send(PasswordReset reset) {
        SimpleMailMessage message = generate(reset.getUser().getEmail());
        message.setSubject(passwordResetSubject);
        message.setText(passwordResetText.formatted(reset.getToken()));

//        javaMailSender.send(message);
        System.out.println(reset.getToken());
        log.info(LogConstant.EMAIL_SENT, reset.getUser(), reset);
    }

    /**
     * Generates a message with the user email.
     * @param email
     * @return SimpleMailMessage
     */
    public SimpleMailMessage generate(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);

        return message;
    }

}
