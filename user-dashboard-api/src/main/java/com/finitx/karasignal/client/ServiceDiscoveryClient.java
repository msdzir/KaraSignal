package com.finitx.karasignal.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceDiscoveryClient {

    private final DiscoveryClient discoveryClient;

    @Value("${microservices.auth}")
    private String auth;

    @Value("${microservices.notification}")
    private String notification;

    @Value("${microservices.subscription}")
    private String subscription;

    @Value("${microservices.market}")
    private String market;

    public String getUrlOfServices(String serviceName){
        return switch (serviceName) {
            case "auth" -> getUrlOfInstance(discoveryClient.getInstances(auth), 0);
            case "notification" -> getUrlOfInstance(discoveryClient.getInstances(notification), 0);
            case "subscription" -> getUrlOfInstance(discoveryClient.getInstances(subscription), 0);
            case "market" -> getUrlOfInstance(discoveryClient.getInstances(market), 0);
            default -> throw new RuntimeException(String.format("The Service Name = %s is not valid.", serviceName));
        };
    }

    private String getUrlOfInstance(List<ServiceInstance> instances, int index){
        if (instances != null && instances.size() > 0) {
            return instances.get(index).getUri().toString();
        } else {
            throw new RuntimeException("Cannot Resolve Target Instances from Service Discovery");
        }
    }
}
