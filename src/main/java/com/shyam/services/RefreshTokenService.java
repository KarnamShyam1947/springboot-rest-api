package com.shyam.services;

import java.util.UUID;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shyam.config.custom.MyUserDetails;
import com.shyam.entities.RefreshTokenEntity;
import com.shyam.repositories.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
       
    @Value("${application.jwt.refresh.experiation}")
    private long refreshExperiation;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenEntity buildRefreshToken(MyUserDetails userDetails) {
        RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity
                                                    .builder()
                                                    .userId(userDetails.getId())
                                                    .refreshToken(UUID.randomUUID().toString())
                                                    .experiation(LocalDateTime.now().plus(refreshExperiation, ChronoUnit.MILLIS))
                                                    .build();
                                        
        return refreshTokenRepository.save(refreshTokenEntity);
    }

    public RefreshTokenEntity validateRefreshToken(String token) {
        RefreshTokenEntity refreshToken = refreshTokenRepository.findByRefreshToken(token);

        if (refreshToken == null) 
            throw new RuntimeException("Invalid refresh token.");

        else if (refreshToken.getExperiation().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token is also expired. Please sign in to make new refresh token");
        }
        
        return refreshToken;
    }

    public RefreshTokenEntity getByUserId(int userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    public RefreshTokenEntity extendExperiarion(RefreshTokenEntity token) {
        token.setExperiation(LocalDateTime.now().plus(refreshExperiation, ChronoUnit.MILLIS));
        return refreshTokenRepository.save(token);
    }
}
