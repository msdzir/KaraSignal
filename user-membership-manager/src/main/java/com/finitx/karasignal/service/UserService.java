package com.finitx.karasignal.service;

import com.finitx.karasignal.constant.ErrorConstant;
import com.finitx.karasignal.constant.LogConstant;
import com.finitx.karasignal.dto.UserRequest;
import com.finitx.karasignal.exception.IllegalUserRequestException;
import com.finitx.karasignal.exception.UserNotFoundException;
import com.finitx.karasignal.model.Role;
import com.finitx.karasignal.model.User;
import com.finitx.karasignal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for mainly CRUD operations for user entity. It also, handles UserDetailsService method.
 *
 * @author Amin Norouzi
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Gets a UserRequest object and creates/saves a user to database from it. Checks if request is valid before taking
     * further actions.
     *
     * @param request
     * @return User
     */
    public User create(UserRequest request) {
        exists(request.getEmail(), request.getPhone());

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(hashedPassword)
                .phone(request.getPhone())
                .role(Role.USER)
                .enabled(false)
                .build();

        User saved = userRepository.save(user);

        log.info(LogConstant.USER_CREATED, saved);
        return saved;
    }

    /**
     * Gets a user and updates its 'enabled' field to true.
     *
     * @param user
     */
    public void enable(User user) {
        user.setEnabled(true);

        userRepository.save(user);
        log.info(LogConstant.USER_ENABLED, user);
    }

    /**
     * Checks if requested email and phone are already existed in the database. If so it throws IllegalUserRequestException
     * exception.
     *
     * @param email
     * @param phone
     */
    public void exists(String email, String phone) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalUserRequestException(ErrorConstant.USER_EMAIL_EXISTS);
        }
        if (userRepository.existsByPhone(phone)) {
            throw new IllegalUserRequestException(ErrorConstant.USER_PHONE_EXISTS);
        }
    }

    /**
     * Returns a user by given id and if it doesn't exist it throws UserNotFoundException exception.
     *
     * @param id
     * @return
     */
    public User get(Long id) {
        User found = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorConstant.USER_NOT_FOUND));

        log.info(LogConstant.USER_FOUND, found);
        return found;
    }

    /**
     * Returns a user by given email and if it doesn't exist it throws UserNotFoundException exception.
     *
     * @param email
     * @return
     */
    public User getByEmail(String email) {
        User found = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(ErrorConstant.USER_NOT_FOUND));

        log.info(LogConstant.USER_FOUND, found);
        return found;
    }

    /**
     * Returns all users by given Pageable object.
     *
     * @param page
     * @param size
     * @param sort
     * @return Page<User>
     */
    public Page<User> getAll(Integer page, Integer size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));

        Page<User> found = userRepository.findAll(pageable);

        log.info(LogConstant.USER_FOUND_ALL, pageable, found);
        return found;
    }

    /**
     * Deletes a user by give id and if it doesn't exist it throws UserNotFoundException exception.
     *
     * @param id
     */
    public void delete(Long id) {
        User user = get(id);
        userRepository.delete(user);

        log.info(LogConstant.USER_DELETED, user);
    }

    /**
     * Gets a username and tries to find a user that matches the given info. This method mainly is handled by Spring Security.
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorConstant.USERNAME_NOT_FOUND));
    }

    public void reset(User user, String password) {
        String hashedPassword = passwordEncoder.encode(password);

        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
}
