package com.eshopingzone.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {
	// hi
    private static String secretKey;
    private static long expirationTime;
    private static long refreshExpirationTime;

    @Value("${jwt.secret}")
    public void setSecretKey(String secret) {
        JwtUtil.secretKey = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpirationTime(long expiration) {
        JwtUtil.expirationTime = expiration;
    }

    @Value("${jwt.refresh-expiration}")
    public void setRefreshExpirationTime(long refreshExpiration) {
        JwtUtil.refreshExpirationTime = refreshExpiration;
    }

    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
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

    // Extract userId from token
    public static String getUserId(Claims claims) {
        return claims.get("userId", String.class);
    }

    // Generate new access token
    public static String generateAccessToken(String userId, String email, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("userId", userId);
        claims.put("tokenType", "access");
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Generate refresh token
    public static String generateRefreshToken(String userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("tokenType", "refresh");
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
