package com.finitx.karasignal.model.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {

    @NotNull
    @NotBlank
    @Email(regexp = "^(.+)@(\\S+)$")
    private String email;

    @NotNull
    @NotBlank
    @ToString.Exclude
    private String password;
}
