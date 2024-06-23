package com.shyam.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RefreshTokenResponse {
    private String accessToekn;
    private String refreshToekn;
}
