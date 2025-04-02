package com.eshopingzone.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.eshopingzone.util.JwtUtil;


@Component
public class JwtAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // Skip JWT validation for public paths and OPTIONS requests
        if (request.getMethod() == HttpMethod.OPTIONS ||
                path.startsWith("/profile-service/api/user/login") ||
                path.startsWith("/profile-service/api/user/register") ||
                path.startsWith("/product-service/api/public/products") ||
                path.startsWith("/address-service/api/address") ||
                path.startsWith("/profile-service/api/users/**")) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtUtil.validateToken(token);

                if (!jwtUtil.isTokenExpired(claims)) {
                    String username = jwtUtil.getEmail(claims);
                    String role = jwtUtil.getRoles(claims);

                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(role));

                    UsernamePasswordAuthenticationToken auth =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    return chain.filter(exchange)
                            .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
                } else {
                    System.err.println("Token is expired");
                }
            } catch (Exception e) {
                System.err.println("JWT Authentication error: " + e.getMessage());
                e.printStackTrace();
            }
        }

        // Continue the chain without authentication for requests without a valid token
        return chain.filter(exchange);
    }
}