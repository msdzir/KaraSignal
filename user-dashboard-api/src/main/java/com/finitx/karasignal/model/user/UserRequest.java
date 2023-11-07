package com.finitx.karasignal.model.user;

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
    @Pattern(regexp = "\\d+")
    private String phone;

    @NotNull
    @NotBlank
    @ToString.Exclude
    private String password;

    @NotNull
    @NotBlank
    @ToString.Exclude
    private String confirmPassword;
}