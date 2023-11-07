package com.finitx.karasignal.client;

import com.finitx.karasignal.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TelegramClient {

    @Value("${telegram.bot.api.send-message}")
    private String messageUrl;

    public void send(Message message) {
        RestTemplate rest = new RestTemplate();

        String url = messageUrl.formatted(message.getTo(), message.getText());

        try {
            rest.getForEntity(url, Void.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
