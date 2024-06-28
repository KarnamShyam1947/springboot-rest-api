package com.shyam.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "email(username) id required")
    private String username;

    @NotBlank(message = "password id required")
    private String password;
}
