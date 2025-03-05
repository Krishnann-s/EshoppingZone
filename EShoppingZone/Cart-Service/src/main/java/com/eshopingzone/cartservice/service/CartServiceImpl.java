package com.eshopingzone.cartservice.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import com.eshopingzone.cartservice.client.ProductClient;
import com.eshopingzone.cartservice.exception.APIException;
import com.eshopingzone.cartservice.model.Cart;
import com.eshopingzone.cartservice.payload.CartDTO;
import com.eshopingzone.cartservice.payload.ProductDTO;
import com.eshopingzone.cartservice.repository.CartItemRepository;
import com.eshopingzone.cartservice.util.AuthUtil;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private CartItemRepository cartItemRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductClient productClient;

	private Cart createCart() {
		Cart userCart = cartRepo.findCartByEmail(authUtil.loggedInEmail());
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
		Cart cart = createCart();
		ProductDTO product = productClient.getProductById(productId);

		Optional<CartItem> cartItem = cartItemRepository.findByProductIdAndCart_CartId(productId, cart.getCartId());
		
		if(cartItem.isPresent()) {
			throw new APIException("Product already present");
		}
		if(product.getQuantity() == 0) {
			throw new APIException(product.getProductName() + " is not available");
		}
		if(product.getQuantity() < quantity) {
			throw new APIException("Please make an order of " + product.getProductName()
			+ " less than or equal to the quantity " + product.getQuantity() + ".");
		}
		
		CartItem newCartItem = new CartItem();

		newCartItem.setProductId(productId);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(quantity);
		newCartItem.setDiscount(product.getDiscount());
		newCartItem.setProductPrice(product.getSpecialPrice());

		cartItemRepository.save(newCartItem);
		product.setQuantity(product.getQuantity());

		cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
		cartRepo.save(cart);

		CartDTO cartDto = modelMapper.map(cart, CartDTO.class);
		cartDto.setProducts(cart.getCartItems().stream().map(item -> {
			ProductDTO prodDto = modelMapper.map(item, ProductDTO.class);
			prodDto.setQuantity(item.getQuantity());
			return prodDto;
		}).toList());

		return cartDto;
	}
}
