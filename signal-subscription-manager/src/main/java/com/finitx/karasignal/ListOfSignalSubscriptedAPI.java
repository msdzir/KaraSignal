package com.finitx.karasignal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ListOfSignalSubscriptedAPI {

	public static void main(String[] args) {
		SpringApplication.run(ListOfSignalSubscriptedAPI.class, args);
	}

}
