package com.finitx.karasignal.service;

import com.finitx.karasignal.constant.LogConstant;
import com.finitx.karasignal.constant.MessageConstant;
import com.finitx.karasignal.dto.*;
import com.finitx.karasignal.model.PasswordReset;
import com.finitx.karasignal.model.User;
import com.finitx.karasignal.model.Verification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * This class is responsible for authenticating user activity like registration, login, verification, and so on.
 *
 * @author Amin Norouzi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationService verificationService;
    private final TokenService tokenService;
    private final EmailService emailService;
    private final PasswordResetService passwordResetService;

    /**
     * Gets a UserRequest object and saves the user to database. Then sends and email to user in async way. In the end,
     * creates and saves access/refresh tokens and returns it to user as AuthResponse.
     *
     * @param request
     * @return AuthResponse
     */
    public AuthResponse signup(UserRequest request) {
        User user = userService.create(request);
        Verification verification = verificationService.create(user);

        emailService.send(verification);

        String access = jwtService.generateAccessToken(user);
        String refresh = jwtService.generateRefreshToken(user);

        tokenService.save(user, access);

        log.info(LogConstant.AUTH_SIGNUP, user);
        return new AuthResponse(access, refresh);
    }

    /**
     * Gets user username and password from controller as AuthRequest then tries to authenticate user in order to return
     * login information. Checks and revokes the user token and generates new access/refresh tokens and saves them before
     * returning it as AuthResponse.
     *
     * @param request
     * @return AuthResponse
     */
    public AuthResponse signin(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userService.getByEmail(request.getEmail());

        String access = jwtService.generateAccessToken(user);
        String refresh = jwtService.generateRefreshToken(user);

        tokenService.revokeAll(user);
        tokenService.save(user, access);

        log.info(LogConstant.AUTH_SIGNIN, user);
        return new AuthResponse(access, refresh);
    }

    /**
     * Gets an email verification token and checks if it is valid. If successful, updates Verification as confirmed and
     * User as enabled.
     *
     * @param token
     */
    public void confirm(String token) {
        verificationService.confirm(token);
    }

    /**
     * Gets a refresh token in HttpServletRequest header and checks it if it is valid will generate a new access token
     * and save it to database.
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        tokenService.refresh(request, response);
    }

    /**
     * Gets a user email and sends a password reset email with token to it.
     *
     * @param email
     * @return PasswordResetResponse
     */
    public PasswordResetResponse forgot(String email) {
        User user = userService.getByEmail(email);
        PasswordReset reset = passwordResetService.create(user);

        emailService.send(reset);

        return new PasswordResetResponse(MessageConstant.PASSWORD_RESET_SENT);
    }

    /**
     * Gets a PasswordResetRequest and validates it if it is valid will save the new password for user. It also revokes
     * all user tokens because of the password change
     *
     * @param request
     * @return PasswordResetResponse
     */
    public PasswordResetResponse reset(PasswordResetRequest request) {
        PasswordReset reset = passwordResetService.reset(request);

        User user = reset.getUser();
        tokenService.revokeAll(user);

        return new PasswordResetResponse(MessageConstant.PASSWORD_RESET_DONE);
    }
}
