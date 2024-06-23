package com.shyam.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {
    private String name;
    private String role;
    private String email;
    private String token;
    private String response;
    private String refreshToken;
}
