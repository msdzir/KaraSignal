package com.finitx.karasignal.controller;

import com.finitx.karasignal.dto.*;
import com.finitx.karasignal.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

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

    @PostMapping(value = "/token")
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authService.refresh(request, response);
    }

    @PostMapping(value = "/forgot-password")
    public PasswordResetResponse forget(@RequestParam String email) {
        return authService.forgot(email);
    }

    @PostMapping(value = "/reset-password")
    public PasswordResetResponse reset(@Valid @RequestBody PasswordResetRequest request) {
        return authService.reset(request);
    }
}