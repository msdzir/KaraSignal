package com.finitx.karasignal.service;

import com.finitx.karasignal.model.Message;
import org.springframework.scheduling.annotation.Async;

public interface NotificationService {

    @Async
    void send(Message message);

    void verify(Message message);
}
