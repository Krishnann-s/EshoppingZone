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
		config.setAllowedOrigins(
				Arrays.asList("http://localhost:5173", "http://localhost:3000", "http://localhost:5174"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setExposedHeaders(Arrays.asList("Authorization"));
		config.setAllowCredentials(true);
		config.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return http.cors(cors -> cors.configurationSource(source)).csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(auth -> auth.pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()

						// Public APIs
						.pathMatchers("/profile-service/api/user/login", "/profile-service/api/user/register",
								"/product-service/api/public/products/**",
								"/product-service/api/public/product/{productId}/image", "/address-service/api/address",
								"/profile-service/api/users/**")
						.permitAll()

						// Profile Service Authorization
						.pathMatchers(HttpMethod.GET, "/profile-service/api/users").hasRole("admin")
						.pathMatchers(HttpMethod.GET, "/profile-service/api/user/**").hasRole("user")
						.pathMatchers(HttpMethod.PUT, "/profile-service/api/update/user/**").hasRole("user")
						.pathMatchers(HttpMethod.DELETE, "/profile-service/api/delete/user/**")
						.hasAnyRole("user", "admin")

						// Address Service Authorization
						.pathMatchers(HttpMethod.GET, "/address-service/api/address/**").hasRole("user")
						.pathMatchers(HttpMethod.GET, "/address-service/api/addresses").hasRole("admin")
						.pathMatchers(HttpMethod.GET, "/address-service/api/user/address").hasRole("user")
						.pathMatchers(HttpMethod.PUT, "/address-service/api/address/**").hasRole("user")
						.pathMatchers(HttpMethod.DELETE, "/address-service/api/address/**").hasAnyRole("admin", "user")

						// Product Service Authorization
						.pathMatchers(HttpMethod.GET, "/product-service/api/public/categories/**")
						.hasAnyRole("user", "admin")
						.pathMatchers(HttpMethod.POST, "/product-service/api/admin/categories/**").hasRole("admin")
						.pathMatchers(HttpMethod.PUT, "/product-service/api/public/categories/**").hasRole("admin")
						.pathMatchers(HttpMethod.DELETE, "/product-service/api/admin/categories/**").hasRole("admin")
						.pathMatchers(HttpMethod.PUT, "/product-service/api/admin/product/**").hasRole("admin")
						.pathMatchers(HttpMethod.DELETE, "/product-service/api/admin/products/**").hasRole("admin")
						.pathMatchers(HttpMethod.POST, "/product-service/api/admin/product/**").hasRole("admin") // add product image
						.pathMatchers(HttpMethod.GET, "/product-service/api/admin/product/**").hasRole("admin") // get product image
						.pathMatchers(HttpMethod.DELETE, "/product-service/api/admin/product/**").hasRole("admin") // delete product image

						// Cart Service Authorization
						.pathMatchers(HttpMethod.GET, "/cart-service/api/carts/**").hasRole("user") // Authorization for get all carts,get cart by id
						.pathMatchers(HttpMethod.POST, "/cart-service/api/**").hasAnyRole("user", "admin") // Authorization for adding products to cart
						.pathMatchers(HttpMethod.PUT, "/cart-service/api/cart/products/**").hasRole("user") // Authorization for updating products in cart
						.pathMatchers(HttpMethod.DELETE, "/cart-service/api/carts/**").hasRole("user") // Authorization for deleting products in Cart

						// Order Service Authorization
						.pathMatchers(HttpMethod.POST, "/order-service/api/order/users/payments/**").hasRole("user")

						// Image Service Authorization
						.pathMatchers(HttpMethod.POST, "/image-service/api/**").hasAnyRole("user", "admin")
						.pathMatchers(HttpMethod.GET, "/image-service/api/**").permitAll()
						.pathMatchers(HttpMethod.DELETE, "/image-service/api/**").hasAnyRole("user", "admin")

						// Everything else requires authentication
						.anyExchange().authenticated())
				.addFilterAt(filter, SecurityWebFiltersOrder.AUTHENTICATION).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}