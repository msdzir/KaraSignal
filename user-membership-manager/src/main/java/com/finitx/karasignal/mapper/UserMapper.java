package com.finitx.karasignal.mapper;

import com.finitx.karasignal.dto.UserResponse;
import com.finitx.karasignal.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

/**
 * This class is responsible for mapping user entity to dto.
 *
 * @author Amin Norouzi
 */
@Configuration
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    /**
     * Converts user entity to dto.
     *
     * @param user
     * @return UserResponse
     */
    public UserResponse toDto(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

    /**
     * Converts a page of users to a page of dto.
     *
     * @param users
     * @return UserResponse.Pageable
     */
    public UserResponse.Pageable toDto(Page<User> users) {
        return modelMapper.map(users, UserResponse.Pageable.class);
    }
}