package com.shyam.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotNull(message = "email(username) id required")
    private String username;

    @NotNull(message = "password id required")
    private String password;
}
