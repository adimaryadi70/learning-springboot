package com.learning.tools;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private static final String secret_key = "1adimaryadi1";
    private static final long tokenExpired = 1000*60;
    private JwtParser jwtParser;

    public static String generateToken(String username) {
        return Jwts.builder()
                   .setSubject(username)
                   .setExpiration(new Date(System.currentTimeMillis()+tokenExpired))
                   .signWith(SignatureAlgorithm.HS512, secret_key)
                   .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return jwtParser.parseSignedClaims(token).getBody();
    }
}
