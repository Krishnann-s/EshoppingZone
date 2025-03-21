package com.eshopingzone.cartservice.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.eshopingzone.cartservice.client.ProductClient;
import com.eshopingzone.cartservice.exception.APIException;
import com.eshopingzone.cartservice.model.Cart;
import com.eshopingzone.cartservice.payload.CartDTO;
import com.eshopingzone.cartservice.payload.ProductDTO;
import com.eshopingzone.cartservice.repository.CartItemRepository;
import com.eshopingzone.cartservice.util.AuthUtil;

import jakarta.transaction.Transactional;

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
		Cart userCart = cartRepo.findCartByProfileId(authUtil.loggedInUserId());
		if (userCart != null) {
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

		CartItem cartItem = cartItemRepository.findByProductIdAndCart_CartId(productId, cart.getCartId());

		if (product.getQuantity() == 0) {
			throw new APIException(product.getProductName() + " is not available");
		}
		if (product.getQuantity() < quantity) {
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

	@Override
	public List<CartDTO> getAllCarts() {
		List<Cart> carts = cartRepo.findAll();

		if (carts.isEmpty()) {
			throw new APIException("No Cart Exists.");
		}

		return carts.stream().map(cart -> {
			CartDTO cartDto = modelMapper.map(cart, CartDTO.class);
			
			List<ProductDTO> products = cart.getCartItems().stream().map(cartItem -> {
				ProductDTO productDto = productClient.getProductById(cartItem.getProductId());
				productDto.setQuantity(cartItem.getQuantity());
				return productDto;
			}).toList();

			cartDto.setProducts(products);

			return cartDto;
		}).toList();
	}

	// Get Cart and products
	@Override
    public CartDTO getCart(Long profileId, Long cartId) {
        Cart cart = cartRepo.findCartByProfileIdAndCartId(profileId, cartId);

        if (cart == null) {
            throw new APIException("CartId not found");
        }

        CartDTO cartDto = modelMapper.map(cart, CartDTO.class);

        // Map CartItems to ProductDTOs
        List<ProductDTO> productDTOs = cart.getCartItems().stream()
            .map(cartItem -> {
                ProductDTO productDTO = productClient.getProductById(cartItem.getProductId());
                productDTO.setQuantity(cartItem.getQuantity());
                productDTO.setDiscount(cartItem.getDiscount());
                productDTO.setPrice(cartItem.getProductPrice());
                return productDTO;
            })
            .collect(Collectors.toList());

        cartDto.setProducts(productDTOs);

        return cartDto;
    }

	@Transactional
	@Override
	public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {
		Long profileId = authUtil.loggedInUserId();
		Cart userCart = cartRepo.findCartByProfileId(profileId);
		Long cartId = userCart.getCartId();

		Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new APIException("Cart Id not found"));

		ProductDTO productDto = productClient.getProductById(productId);

		if (productDto.getQuantity() == 0) {
			throw new APIException(productDto.getProductName() + " is not available.");
		}
		if (productDto.getQuantity() < quantity) {
			throw new APIException("Please make an order of the " + productDto.getProductName()
					+ " less than or equal to quantity" + productDto.getQuantity() + ".");
		}

		CartItem cartItem = cartItemRepository.findByProductIdAndCart_CartId(productId, cartId);

		if (cartItem == null) {
			throw new APIException("Product" + productDto.getProductName() + " not available in cart");
		}

		int newQuantity = cartItem.getQuantity() + quantity;
		if (newQuantity < 0) {
			throw new APIException("The quantity cannot be negative or zero");
		}

		if (newQuantity == 0) {
			deleteProductFromCart(cartId, productId);
		} else {
			cartItem.setProductPrice(productDto.getSpecialPrice());
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
			cartItem.setDiscount(cartItem.getDiscount());
			double priceChange = cartItem.getProductPrice() * quantity;
			cart.setTotalPrice(cart.getTotalPrice() + priceChange);
			cartRepo.save(cart);
		}

		CartItem updatedItem = cartItemRepository.save(cartItem);
		if (updatedItem.getQuantity() == 0) {
			cartItemRepository.deleteById(updatedItem.getCartItemId());
		}

		CartDTO cartDto = modelMapper.map(cart, CartDTO.class);
		List<CartItem> cartItems = cart.getCartItems();

		List<ProductDTO> products = cart.getCartItems().stream().map(item -> {
			ProductDTO prd = productClient.getProductById(item.getProductId());
			prd.setQuantity(item.getQuantity());
			return prd;
		}).collect(Collectors.toList());

		cartDto.setProducts(products);
		return cartDto;
	}

	@Transactional
	@Override
	public String deleteProductFromCart(Long cartId, Long productId) {
		Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new APIException("Cart Id not found"));
		CartItem cartItem = cartItemRepository.findByProductIdAndCart_CartId(productId, cartId);

		if (cartItem == null) {
			throw new APIException("");
		}

		cart.setTotalPrice(cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity()));

		cartItemRepository.delete(cartItem);
		return "Product " + productClient.getProductById(productId).getProductName() + " removed from cart.";
	}
}
