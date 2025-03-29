package com.eshopingzone.cartservice.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.eshopingzone.cartservice.client.AddressClient;
import com.eshopingzone.cartservice.exception.ResourceNotFoundException;
import com.eshopingzone.cartservice.model.Order;
import com.eshopingzone.cartservice.model.OrderItem;
import com.eshopingzone.cartservice.model.Payment;
import com.eshopingzone.cartservice.payload.AddressDTO;
import com.eshopingzone.cartservice.payload.CartDTO;
import com.eshopingzone.cartservice.payload.CartItemDTO;
import com.eshopingzone.cartservice.payload.OrderDTO;
import com.eshopingzone.cartservice.payload.OrderItemDTO;
import com.eshopingzone.cartservice.repository.CartRepository;
import com.eshopingzone.cartservice.repository.OrderItemRepository;
import com.eshopingzone.cartservice.repository.OrderRepository;
import com.eshopingzone.cartservice.repository.PaymentRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private AddressClient addressClient;
	
	@Autowired
	private OrderItemRepository orderItemRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Transactional
	@Override
	public OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId,
			String pgStatus, String pgResponseMessage) {
		
		 String token = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		// Fetch Cart details
		CartDTO cart = cartRepo.getCartByEmail(emailId, token);
		if(cart == null || cart.getCartItems().isEmpty()) {
			throw new ResourceNotFoundException("Cart is emtpy");
		}
		
		// Debug logging for cart items
	    log.info("Cart items received from cart service:");
	    for(CartItemDTO item : cart.getCartItems()) {
	        log.info("Item - ProductId: {}, Name: {}, Quantity: {}, Price: {}", 
	                item.getProductId(), item.getProductName(), item.getQuantity(), item.getProductPrice());
	        
	        // Additional check for null productId
	        if(item.getProductId() == null) {
	            log.error("Null productId found in cart item with name: {}", item.getProductName());
	        }
	    }

		
		// Fetch Address details
		AddressDTO address = addressClient.getAddressById(addressId, request.getHeader(HttpHeaders.AUTHORIZATION));
		if(address == null) {
            throw new ResourceNotFoundException("Address not found with ID: " + addressId);
        }
		
		// Create Order
		Order order = new Order();
		order.setEmail(emailId);
		order.setOrderDate(LocalDate.now());
		order.setTotalAmount(cart.getTotalPrice());
		order.setOrderStatus("order Accepted");
		order.setAddressId(addressId);
		
		// Create Payment
		Payment payment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
		payment.setOrder(order);
		payment = paymentRepo.save(payment);
		order.setPayment(payment);
		
		Order savedOrder = orderRepo.save(order);
		log.info("Order created with Id: {}", savedOrder.getOrderId());

		// Convert cart items to order items
		List<OrderItem> orderItems = new ArrayList<>();
		for(CartItemDTO cartItem : cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(cartItem.getProductId());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setDiscount(cartItem.getDiscount());
			orderItem.setOrderedProductPrice(cartItem.getProductPrice());
			orderItem.setOrder(savedOrder);
			orderItems.add(orderItem);
		}
		orderItems = orderItemRepo.saveAll(orderItems);
		log.info("Saved {} order items", orderItems.size());
		
		// Clear Cart after successful order placement
		try {
		    cartRepo.deleteProductsForUserByEmail(emailId, token);
		    log.info("Cart cleared for user: {}", emailId);
		} catch (Exception e) {
		    log.error("Failed to clear cart for user: {}. Error: {}", emailId, e.getMessage());
		    // We don't throw here as order is already placed
		}
		
		OrderDTO orderDto = modelMapper.map(savedOrder, OrderDTO.class);
		orderItems.forEach(item -> orderDto.getOrderItems().add(modelMapper.map(item, OrderItemDTO.class)));
		
		orderDto.setAddressId(addressId);
		return orderDto;
	}

}
