package com.shyam.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshTokenRequest {
    
    @NotBlank(message = "refresh token is required")
    private String refreshToken;
}
