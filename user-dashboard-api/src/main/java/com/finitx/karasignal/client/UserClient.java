package com.finitx.karasignal.client;

import com.finitx.karasignal.model.user.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "UserClient", url = "http://localhost:8080/api/v1/users")
public interface UserClient {

    @GetMapping("/me")
    UserResponse getByAuthentication(@RequestHeader("Authorization") String token);
}
