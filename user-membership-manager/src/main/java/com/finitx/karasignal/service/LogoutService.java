package com.finitx.karasignal.service;

import com.finitx.karasignal.constant.LogConstant;
import com.finitx.karasignal.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for user logout and handled by Spring Security.
 *
 * @author Amin Norouzi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    /**
     * Gets HttpServletRequest and if it was valid, revokes the principal access token and continues the principal logout
     * process.
     *
     * @param request        the HTTP request
     * @param response       the HTTP response
     * @param authentication the current principal details
     */
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtUtil.isValidHeader(header)) {
            return;
        }

        String jwt = jwtUtil.extractHeader(header);

        tokenService.revoke(jwt);
        log.info(LogConstant.USER_LOGGED_OUT, authentication.getPrincipal());
    }
}
