package com.finitx.karasignal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finitx.karasignal.constant.ErrorConstant;
import com.finitx.karasignal.constant.LogConstant;
import com.finitx.karasignal.dto.AuthResponse;
import com.finitx.karasignal.exception.IllegalTokenRequestException;
import com.finitx.karasignal.exception.TokenNotFoundException;
import com.finitx.karasignal.model.Token;
import com.finitx.karasignal.model.User;
import com.finitx.karasignal.repository.TokenRepository;
import com.finitx.karasignal.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

/**
 * This class is responsible for managing a user access tokens.
 *
 * @author Amin Norouzi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * Gets a user and an access token and returns a Token object that is created/saved based on the given information.
     *
     * @param user
     * @param jwt
     * @return Token
     */
    public Token save(User user, String jwt) {
        Token token = Token.builder()
                .jwt(jwt)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();

        Token saved = tokenRepository.save(token);

        log.info(LogConstant.TOKEN_SAVED, saved);
        return saved;
    }

    /**
     * Returns a Token object based on given id and may throw TokenNotFoundException exception.
     *
     * @param id
     * @return Token
     */
    public Token get(Long id) {
        Token found = tokenRepository.findById(id)
                .orElseThrow(() -> new TokenNotFoundException(ErrorConstant.TOKEN_NOT_FOUND));

        log.info(LogConstant.TOKEN_FOUND, found);
        return found;
    }

    /**
     * Returns a Token object based on given id and may throw TokenNotFoundException exception.
     *
     * @param jwt
     * @return Token
     */
    public Token getByJwt(String jwt) {
        Token found = tokenRepository.findByJwt(jwt)
                .orElseThrow(() -> new TokenNotFoundException(ErrorConstant.TOKEN_NOT_FOUND));

        log.info(LogConstant.TOKEN_FOUND, found);
        return found;
    }

    /**
     * Gets an access token and sets the 'revoke' field as true.
     *
     * @param jwt
     */
    public void revoke(String jwt) {
        Token token = getByJwt(jwt);

        token.setExpired(true);
        token.setRevoked(true);

        tokenRepository.save(token);
        log.info(LogConstant.TOKEN_REVOKED, token);
    }

    /**
     * Gets a user and sets the 'revoke' field as true for it's all tokens.
     *
     * @param user
     */
    @Transactional
    public void revokeAll(User user) {
        List<Token> tokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (tokens.isEmpty()) return;

        tokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });

        tokenRepository.saveAll(tokens);
        log.info(LogConstant.TOKEN_REVOKED_ALL, user);
    }

    /**
     * Gets an access token and validates it if it's either expired or revoked. May throw IllegalTokenRequestException
     * exception.
     *
     * @param jwt
     * @return boolean
     */
    public boolean validate(String jwt) {
        Boolean valid = tokenRepository.findByJwt(jwt)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);

        if (!valid) {
            throw new IllegalTokenRequestException(ErrorConstant.TOKEN_EXPIRED_REVOKED);
        }

        log.info(LogConstant.TOKEN_VALIDATED, jwt);
        return true;
    }

    /**
     * Gets a refresh token from HttpServletRequest and validates it. if successful, will generate new access token and
     * save it to the database. Then will return new tokens to the user.
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtUtil.isValidHeader(header)) {
            throw new IllegalTokenRequestException(ErrorConstant.TOKEN_HEADER_INVALID);
        }

        String refresh = jwtUtil.extractHeader(header);

        if (tokenRepository.existsByJwt(refresh)) {
            throw new IllegalTokenRequestException(ErrorConstant.TOKEN_TYPE_INVALID);
        }

        String email = jwtService.extract(refresh);
        if (StringUtils.isNotEmpty(email)) {
            User user = (User) userService.loadUserByUsername(email);
            if (jwtService.validate(refresh, user)) {
                String access = jwtService.generateAccessToken(user);

                revokeAll(user);
                save(user, access);

                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                response.setStatus(HttpServletResponse.SC_OK);

                new ObjectMapper().writeValue(response.getOutputStream(), new AuthResponse(access, refresh));
            }
        }
    }
}
