package com.eshopingzone.config;

import java.util.Arrays;

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

import com.eshopingzone.filter.JwtAuthenticationFilter;

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

		return http.cors(cors -> cors.configurationSource(source))
				.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(auth -> auth.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

						// Public APIs
						.pathMatchers("/eshoppingzone/user-service/api/user/register",
								"/eshoppingzone/user-service/api/user/login",
								"/eshoppingzone/product-service/api/public/products/**",
								"/eshoppingzone/product-service/api/public/product/{productId}/image",
								"/eshoppingzone/address-service/api/address",
								"/eshoppingzone/user-service/api/users/**",
								"/eshoppingzone/user-service/api/users/profile-picture")
						.permitAll()

						// Profile Service Authorization
						.pathMatchers(HttpMethod.GET, "/eshoppingzone/user-service/api/users").hasRole("admin")
						.pathMatchers(HttpMethod.GET, "/eshoppingzone/user-service/api/user/**").hasAnyRole("user", "admin")
						.pathMatchers(HttpMethod.PUT, "/eshoppingzone/user-service/api/update/user/**").hasRole("user")
						.pathMatchers(HttpMethod.DELETE, "/eshoppingzone/user-service/api/delete/user/**").hasAnyRole("user", "admin")

						// Address Service Authorization
						.pathMatchers(HttpMethod.GET, "/eshoppingzone/user-service/api/address/**").hasRole("user")
						.pathMatchers(HttpMethod.POST, "/eshoppingzone/user-service/api/**").hasRole("user")
						.pathMatchers(HttpMethod.GET, "/eshoppingzone/user-service/api/addresses").hasRole("admin")
						.pathMatchers(HttpMethod.GET, "/eshoppingzone/user-service/api/user/address").hasRole("user")
						.pathMatchers(HttpMethod.PUT, "/eshoppingzone/user-service/api/address/**").hasRole("user")
						.pathMatchers(HttpMethod.DELETE, "/eshoppingzone/user-service/api/address/**").hasAnyRole("admin", "user")

						// Product Service Authorization
						.pathMatchers(HttpMethod.GET, "/eshoppingzone/product-service/api/public/categories/**").permitAll()
						.pathMatchers(HttpMethod.POST, "/eshoppingzone/product-service/api/admin/categories/**").hasRole("admin")
						.pathMatchers(HttpMethod.PUT, "/eshoppingzone/product-service/api/public/categories/**").hasRole("admin")
						.pathMatchers(HttpMethod.DELETE, "/eshoppingzone/product-service/api/admin/categories/**").hasRole("admin")
						.pathMatchers(HttpMethod.PUT, "/eshoppingzone/product-service/api/admin/product/**").hasRole("admin")
						.pathMatchers(HttpMethod.DELETE, "/eshoppingzone/product-service/api/admin/products/**").hasRole("admin")
						.pathMatchers(HttpMethod.POST, "/eshoppingzone/product-service/api/admin/product/**").hasRole("admin") // add product image
						.pathMatchers(HttpMethod.GET, "/eshoppingzone/product-service/api/admin/product/**").hasRole("admin") // get product image
						.pathMatchers(HttpMethod.DELETE, "/eshoppingzone/product-service/api/admin/product/**").hasRole("admin") // delete product image

						// Cart Service Authorization
						.pathMatchers(HttpMethod.GET, "/eshoppingzone/cart-service/api/carts/**").hasRole("user") // Authorization for get all carts,get cart by id
						.pathMatchers(HttpMethod.POST, "/eshoppingzone/cart-service/api/**").hasAnyRole("user", "admin") // Authorization for adding products to cart
						.pathMatchers(HttpMethod.PUT, "/eshoppingzone/cart-service/api/cart/products/**").hasRole("user") // Authorization for updating products in cart
						.pathMatchers(HttpMethod.DELETE, "/eshoppingzone/cart-service/api/carts/**").hasRole("user") // Authorization for deleting products in Cart

						// Order Service Authorization
						.pathMatchers(HttpMethod.POST, "/eshoppingzone/cart-service/api/order/users/payments/**").hasRole("user")

						// Image Service Authorization
						.pathMatchers(HttpMethod.POST, "/eshoppingzone/image-service/api/**").hasAnyRole("user", "admin")
						.pathMatchers(HttpMethod.GET, "/eshoppingzone/image-service/api/**").permitAll()
						.pathMatchers(HttpMethod.DELETE, "/eshoppingzone/image-service/api/**").hasAnyRole("user", "admin")

						// Everything else requires authentication
						.anyExchange().authenticated())
				.addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}