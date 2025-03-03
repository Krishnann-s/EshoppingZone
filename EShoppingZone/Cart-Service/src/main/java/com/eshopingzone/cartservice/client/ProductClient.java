package com.eshopingzone.cartservice.client;

import com.eshopingzone.cartservice.payload.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Product-Service", path = "/api/products")
public interface ProductClient {
    @GetMapping("/{propductId}")
    ProductDTO getProductById(@PathVariable Long productId);
}
