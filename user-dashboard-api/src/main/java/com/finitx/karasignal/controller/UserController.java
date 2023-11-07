package com.finitx.karasignal.controller;

import com.finitx.karasignal.model.user.UserResponse;
import com.finitx.karasignal.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public UserResponse getUserByAuthentication(@RequestHeader("Authorization") String token) {
        return userService.getByAuthentication(token);
    }
}
