package com.finitx.karasignal.service;

import com.finitx.karasignal.client.UserClient;
import com.finitx.karasignal.model.user.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for mainly CRUD operations for user entity. It also, handles UserDetailsService method.
 *
 * @author Amin Norouzi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserClient userClient;

    public UserResponse getByAuthentication(String token) {
        return userClient.getByAuthentication(token);
    }
}
