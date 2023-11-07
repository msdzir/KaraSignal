package com.finitx.karasignal;

import com.finitx.karasignal.client.ServiceDiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
//@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class UserDashboardApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserDashboardApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            ServiceDiscoveryClient discoveryClient
    ) {
        return args -> {
            var url = discoveryClient.getUrlOfServices("auth");
            log.info("Auth Url From Service Discovery = {}", url);

            url = discoveryClient.getUrlOfServices("notification");
            log.info("Notification Url From Service Discovery = {}", url);

            url = discoveryClient.getUrlOfServices("subscription");
            log.info("Subscription Url From Service Discovery = {}", url);

            url = discoveryClient.getUrlOfServices("market");
            log.info("Market Url From Service Discovery = {}", url);
        };
    }

}
