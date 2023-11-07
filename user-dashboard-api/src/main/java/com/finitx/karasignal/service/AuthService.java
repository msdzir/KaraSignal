package com.finitx.karasignal.service;

import com.finitx.karasignal.client.AuthClient;
import com.finitx.karasignal.model.auth.AuthRequest;
import com.finitx.karasignal.model.auth.AuthResponse;
import com.finitx.karasignal.model.password.PasswordResetRequest;
import com.finitx.karasignal.model.password.PasswordResetResponse;
import com.finitx.karasignal.model.user.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthClient authClient;

    public AuthResponse signup(UserRequest request) {
        return authClient.signup(request);
    }

    public AuthResponse signin(AuthRequest request) {
        return authClient.signin(request);
    }

    public void confirm(String token) {
        authClient.confirm(token);
    }

//    public void refresh(String token) {
//        authClient.refresh(token);
//    }

    public PasswordResetResponse forget(String email) {
        return authClient.forget(email);
    }

    public PasswordResetResponse reset(PasswordResetRequest request) {
        return authClient.reset(request);
    }
}
