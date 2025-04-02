package com.eshopingzone.util;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	@Value("${jwt.secret-key}")
	private String secretKey;
    
   
	public Claims validateToken(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}

	private Key getSignKey() {
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
}