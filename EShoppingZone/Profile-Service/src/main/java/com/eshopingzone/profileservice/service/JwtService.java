package com.eshopingzone.profileservice.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

    private static final String SECRET_KEY;
    
    static {
    	String envKey = System.getenv("SECRET_KEY");
    	if(envKey == null || envKey.isEmpty()) {
    		throw new IllegalStateException("Secret Key env is not set");
    	}
    	SECRET_KEY = envKey;
    }
	
	public String generateToken(String userId, String email, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", "ROLE_" + role);
		claims.put("userId", userId); // Add userId to the claims
        claims.put("email", email); // Add email to the claims
        return createtoken(claims, email);
	}
	
	
	private String createtoken(Map<String, Object> claims, String email) {
		return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000* 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}


	public void validateToken(final String token) {
		Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
	}

	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
