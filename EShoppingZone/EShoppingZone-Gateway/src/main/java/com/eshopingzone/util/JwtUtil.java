package com.eshopingzone.util;

import java.security.Key;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.stereotype.Component;


import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "a25b2770a7b1a7f9ee971bfe7e5a9eda12c3d3f022b1a5373d472afca0561316";

    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Check if the token is expired
    public static boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

    // Extract username (subject) from token
    public static String getEmail(Claims claims) {
        return claims.getSubject();
    }

    // Extract roles from token
    public static String getRoles(Claims claims) {
        return claims.get("role", String.class);
    }
}
