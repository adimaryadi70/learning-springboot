package com.learning.tools;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

public class JwtUtil {
    
    private static final String SECRET_KEY = "adumaryadi123";
    private static final long EXPIRATETION_TIME = 864_000_000;
    
    public static String generateToken(String username) {
        return Jwts.builder()
                   .setSubject(username)
                   .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATETION_TIME))
                   .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                   .compact();
    }
}
