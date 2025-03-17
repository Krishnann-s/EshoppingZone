package com.eshopingzone.config;

import java.security.Key;
import java.util.Arrays;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;

import com.eshopingzone.filter.JwtAuthenticationFilter;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter filter;

    public SecurityConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:3000", "http://localhost:5174"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setExposedHeaders(Arrays.asList("Authorization"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return http
            .cors(cors -> cors.configurationSource(source))
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .authorizeExchange(auth -> auth
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                // Public APIs
                .pathMatchers("/profile-service/api/user/login", "/profile-service/api/user/register").permitAll()

                // Secured APIs
                .pathMatchers(HttpMethod.GET, "/profile-service/api/users").hasRole("admin")
                .pathMatchers(HttpMethod.GET, "/profile-service/api/user/**").hasRole("user")
                .pathMatchers(HttpMethod.PUT, "/profile-service/api/update/user/**").hasRole("user")
                .pathMatchers(HttpMethod.DELETE, "/profile-service/api/delete/user/**").hasAnyRole("user", "admin")

                // Product Service Authorization
                .pathMatchers(HttpMethod.GET, "/product-service/api/public/products/**").hasAnyRole("user", "admin")
                .pathMatchers(HttpMethod.GET, "/product-service/api/public/categories/**").hasAnyRole("user", "admin")
                .pathMatchers(HttpMethod.POST, "/product-service/api/admin/categories/**").hasRole("admin")
                .pathMatchers(HttpMethod.PUT, "/product-service/api/public/categories/**").hasRole("admin")
                .pathMatchers(HttpMethod.DELETE, "/product-service/api/admin/categories/**").hasRole("admin")
                .pathMatchers(HttpMethod.PUT, "/product-service/api/admin/product/**").hasRole("admin")
                .pathMatchers(HttpMethod.DELETE, "/product-service/api/admin/products/**").hasRole("admin")

                // Cart Service Authorization
                .pathMatchers(HttpMethod.GET, "/cart-service/api/carts/**").hasRole("user")
                .pathMatchers(HttpMethod.GET, "/cart-service/api/test-auth").hasRole("user")
                .pathMatchers(HttpMethod.POST, "/cart-service/api/carts/products/**").hasRole("user")
                .pathMatchers(HttpMethod.PUT, "/cart-service/api/cart/products/**").hasRole("user")
                .pathMatchers(HttpMethod.DELETE, "/cart-service/api/carts/**").hasRole("user")

                // Everything else requires authentication
                .anyExchange().authenticated()
            )
//            .oauth2ResourceServer(oauth2 -> oauth2.jwt())
            .addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION)
            .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}