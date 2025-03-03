package com.eshopingzone.cartservice.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {

    private Long cartItemId;
    private double productPrice;
    private Integer quantity;
    private double discount;
    private Long productId;
    private Long cartId;
}
