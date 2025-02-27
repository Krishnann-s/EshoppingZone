package com.eshopingzone.cartservice.service;

import java.math.BigDecimal;
import java.util.List;

import com.eshopingzone.cartservice.model.Cart;
import com.eshopingzone.cartservice.payload.CartDTO;
import com.eshopingzone.cartservice.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshopingzone.cartservice.model.CartItem;
import com.eshopingzone.cartservice.repository.CartRepository;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private AuthUtil authUtil;

	private Cart createCart() {
		Cart userCart = cartRepo.findByEmail(authUtil.loggedInEmail());
		if(userCart != null) {
			return userCart;
		}
		Cart cart = new Cart();
		cart.setTotalPrice(0.0);
		cart.setProfileId(authUtil.loggedInUserId());
		Cart newCart = cartRepo.save(cart);

		return newCart;
	}

	@Override
	public CartDTO addProductsToCart(Long productId, int quantity) {
		List<CartItem> cart = cartRepo.findByUserId(userId);

        boolean productExists = false;
        for (CartItem item : cart) {
            if (item.getProductId() == productId) {
                item.setQuantity(item.getQuantity() + quantity);
                productExists = true;
                break;
            }
        }

        if (!productExists) {
            CartItem item = new CartItem();
            item.setUserId(userId);
            item.setProductId(productId);
            item.setProductName(productName);
            item.setProductImage(productImage);
            item.setProductPrice(productPrice);
            item.setQuantity(quantity);
            cartRepo.save(item);
        }

	}

	public void deleteProductFromCart(int userId, int productId) {
		CartItem cartItem = cartRepo.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartRepo.delete(cartItem);
        }
	}

	public List<CartItem> getCartByUserId(int userId) {
		return cartRepo.findByUserId(userId);
	}

	@Override
	public void decreaseProductQuantity(int userId, int productId) {
		List<CartItem> cart = cartRepo.findByUserId(userId);
			for(CartItem item : cart) {
				if(item.getProductId() == productId) {
					if(item.getQuantity() > 1) {
						item.setQuantity(item.getQuantity() - 1);
						cartRepo.save(item);
					} else {
						cartRepo.delete(item);
					}
					return;
				}
			}
	}

	@Override
	public void emptyCart(int userId) {
		cartRepo.deleteByUserId(userId);
	}

}
