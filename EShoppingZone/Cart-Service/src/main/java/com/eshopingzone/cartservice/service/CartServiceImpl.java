package com.eshopingzone.cartservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.eshopingzone.cartservice.client.ProductClient;
import com.eshopingzone.cartservice.client.UserClient;
import com.eshopingzone.cartservice.exception.ResourceNotFoundException;
import com.eshopingzone.cartservice.model.Cart;
import com.eshopingzone.cartservice.model.CartItem;
import com.eshopingzone.cartservice.payload.CartDTO;
import com.eshopingzone.cartservice.payload.CartItemDTO;
import com.eshopingzone.cartservice.payload.ProductDTO;
import com.eshopingzone.cartservice.repository.CartItemRepository;
import com.eshopingzone.cartservice.repository.CartRepository;
import com.eshopingzone.cartservice.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

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
	
	@Autowired
	private UserClient userClient;

	@Autowired
	private HttpServletRequest request;

	// Creating a Cart if it's empty
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

	// Adding Products to the cart
	@Transactional
	@Override
	public CartDTO addProductsToCart(Long productId, int quantity) {
	    // Create cart if cart doesn't exist
	    Cart cart = createCart();
	    
	    // Get Products from productClient
	    ProductDTO product = productClient.getProductById(productId, request.getHeader(HttpHeaders.AUTHORIZATION));

	    // Find if product already exists in the cart
	    CartItem existingCartItem = cartItemRepository.findByProductIdAndCart_CartId(productId, cart.getCartId());

	    // Check if the Product added by the user is 0
	    if (product.getQuantity() == 0) {
	        throw new ResourceNotFoundException(product.getProductName() + " is not available");
	    }
	    
	    // Check if Product quantity in database is greater than the user requested quantity
	    if (product.getQuantity() < quantity) {
	        throw new ResourceNotFoundException("Please make an order of " + product.getProductName()
	                + " less than or equal to the quantity " + product.getQuantity() + ".");
	    }

	    if (existingCartItem != null) {
	        // Product already exists in cart, update quantity
	        existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
	        existingCartItem.setDiscount(product.getDiscount());
	        existingCartItem.setProductPrice(product.getSpecialPrice());
	        
	        cartItemRepository.save(existingCartItem);
	    } else {
	        // Create a new cart item for the cart
	        CartItem newCartItem = new CartItem();
	        
	        // Add Product details to cart Items
	        newCartItem.setProductId(productId);
	        newCartItem.setCart(cart);
	        newCartItem.setQuantity(quantity);
	        newCartItem.setDiscount(product.getDiscount());
	        newCartItem.setProductPrice(product.getSpecialPrice());
	        
	        cartItemRepository.save(newCartItem);
	    }

	    // Update the cart total price
	    cart.setTotalPrice(cart.getTotalPrice() + (product.getSpecialPrice() * quantity));
	    cartRepo.save(cart);

	    // Create a new CartDTO
	    CartDTO cartDto = new CartDTO();
	    cartDto.setCartId(cart.getCartId());
	    cartDto.setTotalPrice(cart.getTotalPrice());
	    
	    // Create products list manually for the DTO
	    List<ProductDTO> productsList = new ArrayList<>();
	    
	    // Add the current product to the list with the correct quantity
	    ProductDTO currentProductDto = new ProductDTO();
	    currentProductDto.setProductName(product.getProductName());
	    currentProductDto.setPrice(product.getPrice());
	    currentProductDto.setDiscount(product.getDiscount());
	    currentProductDto.setSpecialPrice(product.getSpecialPrice());
	    currentProductDto.setDescription(product.getDescription());
	    currentProductDto.setImage(product.getImage());
	    
	    // Set the quantity based on whether it's a new item or existing one
	    if (existingCartItem != null) {
	        currentProductDto.setQuantity(existingCartItem.getQuantity());
	    } else {
	        currentProductDto.setQuantity(quantity);
	    }
	    
	    productsList.add(currentProductDto);
	    
	    // If there are other products in the cart, add them too
	    if (existingCartItem != null) {
	        // Find all cart items except the current one
	        List<CartItem> otherCartItems = cartItemRepository.findByCart_CartIdAndProductIdNot(
	            cart.getCartId(), productId);
	        
	        for (CartItem item : otherCartItems) {
	            ProductDTO otherProductDto = productClient.getProductById(
	                item.getProductId(), request.getHeader(HttpHeaders.AUTHORIZATION));
	            otherProductDto.setQuantity(item.getQuantity());
	            productsList.add(otherProductDto);
	        }
	    }
	    
	    cartDto.setProducts(productsList);
	    
	    return cartDto;
	}

	// Get All carts available (one user has one cart but can one user can have many
	// carts)
	@Override
	public List<CartDTO> getAllCarts() {
		List<Cart> carts = cartRepo.findAll();

		if (carts.isEmpty()) {
			throw new ResourceNotFoundException("No Cart Exists.");
		}

		return carts.stream().map(cart -> {
			CartDTO cartDto = modelMapper.map(cart, CartDTO.class);

			List<ProductDTO> products = cart.getCartItems().stream().map(cartItem -> {
				ProductDTO productDto = productClient.getProductById(cartItem.getProductId(),
						request.getHeader(HttpHeaders.AUTHORIZATION));
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
			throw new ResourceNotFoundException("CartId not found");
		}

		CartDTO cartDto = modelMapper.map(cart, CartDTO.class);
		
		if (cartDto.getCartItems() == null) {
	        cartDto.setCartItems(new ArrayList<>());
	    }
		
		List<CartItemDTO> cartItemDTOs = cart.getCartItems().stream().map(cartItem -> {
	        CartItemDTO cartItemDTO = modelMapper.map(cartItem, CartItemDTO.class);
	        // Set additional properties if needed
	        return cartItemDTO;
	    }).collect(Collectors.toList());
		
		// Map CartItems to ProductDTOs
		List<ProductDTO> productDTOs = cart.getCartItems().stream().map(cartItem -> {
			ProductDTO productDTO = productClient.getProductById(cartItem.getProductId(),
					request.getHeader(HttpHeaders.AUTHORIZATION));
			productDTO.setQuantity(cartItem.getQuantity());
			productDTO.setDiscount(cartItem.getDiscount());
			productDTO.setPrice(cartItem.getProductPrice());
			return productDTO;
		}).collect(Collectors.toList());

		cartDto.setProducts(productDTOs);

		return cartDto;
	}

	// Update the Product Quantity by passing "add" or "delete" in the API parameter
	@Transactional
	@Override
	public CartDTO updateProductQuantityInCart(Long productId, Integer quantity) {
	    Long profileId = authUtil.loggedInUserId();
	    Cart userCart = cartRepo.findCartByProfileId(profileId);
	    Long cartId = userCart.getCartId();

	    Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart Id not found"));

	    ProductDTO productDto = productClient.getProductById(productId, request.getHeader(HttpHeaders.AUTHORIZATION));

	    if (productDto.getQuantity() == 0) {
	        throw new ResourceNotFoundException(productDto.getProductName() + " is not available.");
	    }
	    
	    CartItem cartItem = cartItemRepository.findByProductIdAndCart_CartId(productId, cartId);

	    if (cartItem == null) {
	        throw new ResourceNotFoundException("Product" + productDto.getProductName() + " not available in cart");
	    }

	    int newQuantity = cartItem.getQuantity() + quantity;
	    
	    if (newQuantity <= 0) {
	        // Delete the product from cart
	        deleteProductFromCart(cartId, productId);
	        
	        // Refresh the cart after deletion
	        cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart Id not found"));
	        
	        // Map to DTO and return
	        CartDTO cartDto = modelMapper.map(cart, CartDTO.class);
	        
	        // If cart has any remaining items, populate products
	        if (!cart.getCartItems().isEmpty()) {
	            List<ProductDTO> products = cart.getCartItems().stream().map(item -> {
	                ProductDTO prd = productClient.getProductById(item.getProductId(), request.getHeader(HttpHeaders.AUTHORIZATION));
	                prd.setQuantity(item.getQuantity());
	                return prd;
	            }).collect(Collectors.toList());
	            cartDto.setProducts(products);
	        } else {
	            cartDto.setProducts(List.of()); // Empty list if no products remain
	        }
	        
	        return cartDto;
	    } 
	    else {
	        // Update the item
	        cartItem.setProductPrice(productDto.getSpecialPrice());
	        cartItem.setQuantity(newQuantity);
	        
	        // Recalculate the total price for the cart
	        double priceChange = productDto.getSpecialPrice() * quantity;
	        cart.setTotalPrice(cart.getTotalPrice() + priceChange);
	        
	        // Save changes
	        cartItemRepository.save(cartItem);
	        cartRepo.save(cart);
	        
	        // Refresh cart from database to ensure up-to-date state
	        cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart Id not found"));
	        
	        // Map to DTO and return
	        CartDTO cartDto = modelMapper.map(cart, CartDTO.class);
	        List<ProductDTO> products = cart.getCartItems().stream().map(item -> {
	            ProductDTO prd = productClient.getProductById(item.getProductId(), request.getHeader(HttpHeaders.AUTHORIZATION));
	            prd.setQuantity(item.getQuantity());
	            return prd;
	        }).collect(Collectors.toList());

	        cartDto.setProducts(products);
	        return cartDto;
	    }
	}

	// Delete Products from cart if the quantity goes below 0
	@Transactional
	@Override
	public String deleteProductFromCart(Long cartId, Long productId) {
	    Cart cart = cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart Id not found"));
	    CartItem cartItem = cartItemRepository.findByProductIdAndCart_CartId(productId, cartId);

	    if (cartItem == null) {
	        throw new ResourceNotFoundException("Cart Item is Null");
	    }

	    // Get product name before deletion for message
	    String productName = productClient.getProductById(productId, request.getHeader(HttpHeaders.AUTHORIZATION)).getProductName();
	    
	    // Calculate price to subtract
	    double priceToSubtract = cartItem.getProductPrice() * cartItem.getQuantity();
	    
	    // Remove the item from the cart's collection
	    cart.getCartItems().remove(cartItem);
	    
	    // Delete from repository
	    cartItemRepository.delete(cartItem);
	    
	    // Update total price
	    cart.setTotalPrice(Math.max(0, cart.getTotalPrice() - priceToSubtract));
	    
	    // Save the updated cart
	    cartRepo.save(cart);
	    
	    return "Product " + productName + " removed from cart.";
	}

	// Delete Products by Email
	@Transactional
	@Override
	public String deleteUserProductsByEmail(String email) {
	    // Query for the user's profile ID based on email
	    Long profileId = userClient.getProfileIdByEmail(email);
	    
	    // Find the cart using the profile ID
	    Cart cart = cartRepo.findCartByProfileId(profileId);
	    
	    if (cart == null) {
	        throw new ResourceNotFoundException("Cart not found for user: " + email);
	    }
	    
	    // Get item count before clearing
	    int itemCount = cart.getCartItems().size();
	    
	    // Clear the collection first (this maintains Hibernate session consistency)
	    cart.getCartItems().clear();
	    
	    // Update total price
	    cart.setTotalPrice(0.0);
	    
	    // Save the cart with cleared items
	    cartRepo.save(cart);
	    
	    // Only after saving the cart with cleared collection, delete from the database
	    // This is now a separate operation from the Hibernate session
	    cartItemRepository.deleteByCart_CartId(cart.getCartId());
	    
	    return "Successfully removed " + itemCount + " items from cart for user: " + email;
	}
}
