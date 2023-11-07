package com.finitx.karasignal.client;

import com.finitx.karasignal.model.auth.AuthRequest;
import com.finitx.karasignal.model.auth.AuthResponse;
import com.finitx.karasignal.model.password.PasswordResetRequest;
import com.finitx.karasignal.model.password.PasswordResetResponse;
import com.finitx.karasignal.model.user.UserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("user-membership-manager")
public interface AuthClient {

    @PostMapping("/signup")
    AuthResponse signup(@RequestBody UserRequest request);

    @PostMapping("/signin")
    AuthResponse signin(@RequestBody AuthRequest request);

    @GetMapping("/confirmation?token={token}")
    void confirm(@PathVariable String token);

//    @PostMapping("/token")
//    void refresh(@RequestHeader("Authorization") String token);

    @PostMapping("/forgot-password?email={email}")
    PasswordResetResponse forget(@PathVariable String email);

    @PostMapping("/reset-password")
    PasswordResetResponse reset(@RequestBody PasswordResetRequest request);
}
