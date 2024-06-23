package com.shyam.services;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${application.jwt.secret.key}")
    private String secretKey;

    @Value("${application.jwt.experiation}")
    private long experiation;

    // generate secret key
    public SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // generate token
    public String generateJwtToken(UserDetails userDetails, Map<String, Object> claims) {
        return Jwts
                .builder()
                .signWith(getSecretKey())
                .subject(userDetails.getUsername())
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + experiation)) // 10 min
                .compact();
    }

    public String generateJwtToken(UserDetails userDetails) {
        return generateJwtToken(userDetails, new HashMap<>());
    }

    // extract claims
    public Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // extract single claim
    public <T> T getSingleClaim(String token, Function<Claims, T> resolver) {
        Claims claims = getAllClaims(token);
        return resolver.apply(claims);
    }

    // util methods
    public String getUsername(String token) {
        return getSingleClaim(token, Claims::getSubject);
    }

    //validation method
    public boolean isValidToken(String email, UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return userEmail.equals(email);
    }

}
