package com.shoppingcart.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shoppingcart.DTO.JwtUser;
import com.shoppingcart.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	private final SecretKey secretKey;

    public JwtService(@Value("${jwt.secret}") String secretKeyValue) {

        this.secretKey = Keys.hmacShaKeyFor(
                secretKeyValue.getBytes(StandardCharsets.UTF_8)
        );
    }
    public String generateToken(User user) {

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getUserId())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(
                		new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 10)
                )
                .signWith(
                		secretKey
                )
                .compact();
    }
    
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    
    public JwtUser extractUser(String token) {

        Claims claims = extractClaims(token);

        return new JwtUser(
            claims.get("userId", Long.class),
            claims.getSubject(),
            claims.get("role", String.class)
        );
    }
}