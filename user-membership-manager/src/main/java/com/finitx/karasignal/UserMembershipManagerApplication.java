package com.finitx.karasignal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@EnableJpaAuditing
@EnableDiscoveryClient
@SpringBootApplication
public class UserMembershipManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMembershipManagerApplication.class, args);
    }
}
