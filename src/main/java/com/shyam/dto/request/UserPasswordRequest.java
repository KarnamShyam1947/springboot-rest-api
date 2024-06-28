package com.shyam.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordRequest {
    @NotBlank(message = "Unique token is required")
    private String token;

    @NotBlank(message = "password is required")
    private String password;
}
