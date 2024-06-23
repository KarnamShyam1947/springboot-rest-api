package com.shyam.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequest {
    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "role is required")
    private String role;

    @NotNull(message = "email is required")
    private String email;

    @NotNull(message = "password is required")
    private String password;
}
