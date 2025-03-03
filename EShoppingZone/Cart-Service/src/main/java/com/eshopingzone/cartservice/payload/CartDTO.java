package com.eshopingzone.cartservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private Long cartId;
    private int profileId;
    private double totalPrice= 0.0;
    private List<CartItemDTO> cartItems;
    private List<ProductDTO> products = new ArrayList<>();
}
