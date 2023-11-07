package com.finitx.karasignal.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private Role role;
    private boolean enabled;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Pageable {

        private List<UserResponse> content;
        private Integer numberOfElements;
        private Integer totalPages;
        private Integer totalElements;
    }
}
