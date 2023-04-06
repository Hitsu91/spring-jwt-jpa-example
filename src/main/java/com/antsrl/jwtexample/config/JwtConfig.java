package com.antsrl.jwtexample.config;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.Key;

@Configuration
public class JwtConfig {
    @Value("${jwt.config.key}")
    private String secretKey;

    @Bean
    public JwtParser getJwtParser() {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build();
    }

    @Bean
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
