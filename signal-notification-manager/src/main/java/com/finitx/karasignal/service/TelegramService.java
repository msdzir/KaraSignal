package com.finitx.karasignal.service;

import com.finitx.karasignal.client.TelegramClient;
import com.finitx.karasignal.constant.ErrorConstant;
import com.finitx.karasignal.exception.InvalidTelegramMessageException;
import com.finitx.karasignal.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelegramService implements NotificationService {

    private final TelegramClient telegramClient;

    @Override
    public void send(Message message) {
        verify(message);

        telegramClient.send(message);
    }

    @Override
    public void verify(Message message) {
        if ((message.getTo() == null || message.getTo().isBlank()) ||
                (message.getText() == null || message.getText().isBlank())) {
            throw new InvalidTelegramMessageException(ErrorConstant.INVALID_TELEGRAM_MESSAGE);
        }
    }
}
