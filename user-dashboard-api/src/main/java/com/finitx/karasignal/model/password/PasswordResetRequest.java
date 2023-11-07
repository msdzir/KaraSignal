package com.finitx.karasignal.model.password;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {

    @NotNull
    @NotBlank
    private String token;

    @NotNull
    @NotBlank
    @ToString.Exclude
    private String password;

    @NotNull
    @NotBlank
    @ToString.Exclude
    private String confirmPassword;
}
