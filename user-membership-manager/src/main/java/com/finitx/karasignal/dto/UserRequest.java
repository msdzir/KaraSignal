package com.finitx.karasignal.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    @NotNull
    @NotBlank
    private String fullName;

    @NotNull
    @NotBlank
    @Email(regexp = "^(.+)@(\\S+)$")
    private String email;

    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "\\d+")
    private String phone;

    @NotNull
    @NotBlank
    @ToString.Exclude
    private String password;
}