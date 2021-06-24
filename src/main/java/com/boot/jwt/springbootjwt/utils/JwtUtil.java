package com.boot.jwt.springbootjwt.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class JwtUtil {
    private Key key;

    public JwtUtil(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long id, String name) {
        return Jwts.builder()
                .claim("userId", id)
                .claim("name", name)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
