package com.finitx.karasignal.controller;

import com.finitx.karasignal.model.Message;
import com.finitx.karasignal.service.EmailService;
import com.finitx.karasignal.service.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final EmailService emailService;
    private final TelegramService telegramService;

    @PostMapping("/email")
    public void sendEmailNotification(@RequestBody Message message) {
        emailService.send(message);
    }

    @PostMapping("/telegram")
    public void sendTelegramNotification(@RequestBody Message message) {
        telegramService.send(message);
    }
}
