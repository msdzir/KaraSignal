package com.finitx.karasignal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync(proxyTargetClass = true)
@EnableDiscoveryClient
@SpringBootApplication
public class SignalNotificationManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SignalNotificationManagerApplication.class, args);
    }

}
