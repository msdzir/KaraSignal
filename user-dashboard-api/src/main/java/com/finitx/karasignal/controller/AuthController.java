package com.finitx.karasignal.controller;

import com.finitx.karasignal.model.auth.AuthRequest;
import com.finitx.karasignal.model.auth.AuthResponse;
import com.finitx.karasignal.model.password.PasswordResetRequest;
import com.finitx.karasignal.model.password.PasswordResetResponse;
import com.finitx.karasignal.model.user.UserRequest;
import com.finitx.karasignal.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(value = "/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse signup(@Valid @RequestBody UserRequest request) {
        return authService.signup(request);
    }

    @PostMapping("/signin")
    public AuthResponse signin(@Valid @RequestBody AuthRequest request) {
        return authService.signin(request);
    }

    @GetMapping("/confirmation")
    public void confirm(@RequestParam String token) {
        authService.confirm(token);
    }

//    @PostMapping(value = "/token")
//    public void refresh(@RequestHeader("Authorization") String token) {
//        authService.refresh(token);
//    }

    @PostMapping(value = "/forgot-password")
    public PasswordResetResponse forget(@RequestParam String email) {
        return authService.forget(email);
    }

    @PostMapping(value = "/reset-password")
    public PasswordResetResponse reset(@Valid @RequestBody PasswordResetRequest request) {
        return authService.reset(request);
    }
}