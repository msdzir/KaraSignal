package com.finitx.karasignal.controller;

import com.finitx.karasignal.dto.UserRequest;
import com.finitx.karasignal.dto.UserResponse;
import com.finitx.karasignal.mapper.UserMapper;
import com.finitx.karasignal.model.User;
import com.finitx.karasignal.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@PreAuthorize("isAuthenticated() and principal.isEnabled()")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        User user = userService.create(request);
        return userMapper.toDto(user);
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        User user = userService.get(id);
        return userMapper.toDto(user);
    }

    @GetMapping
    public UserResponse.Pageable getAllUsers(@RequestParam(defaultValue = "0") Integer page,
                                             @RequestParam(defaultValue = "5") Integer size,
                                             @RequestParam(defaultValue = "id") String sort) {
        Page<User> users = userService.getAll(page, size, sort);
        return userMapper.toDto(users);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }
}
