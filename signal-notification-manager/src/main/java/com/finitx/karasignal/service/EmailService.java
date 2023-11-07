package com.finitx.karasignal.service;

import com.finitx.karasignal.constant.ErrorConstant;
import com.finitx.karasignal.exception.InvalidMailMessageException;
import com.finitx.karasignal.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService implements NotificationService {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(Message message) {
        verify(message);

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(message.getTo());
        mail.setSubject(message.getSubject());
        mail.setText(message.getText());

        javaMailSender.send(mail);
    }

    @Override
    public void verify(Message message) {
        if ((message.getTo() == null || message.getTo().isBlank()) ||
                (message.getSubject() == null || message.getSubject().isBlank()) ||
                message.getText() == null) {
            throw new InvalidMailMessageException(ErrorConstant.INVALID_MAIL_MESSAGE);
        }
    }
}
