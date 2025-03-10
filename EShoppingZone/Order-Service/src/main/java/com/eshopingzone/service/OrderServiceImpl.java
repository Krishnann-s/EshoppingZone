package com.eshopingzone.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshopingzone.exception.ResourceNotFoundException;
import com.eshopingzone.model.Order;
import com.eshopingzone.model.OrderItem;
import com.eshopingzone.model.Payment;
import com.eshopingzone.payload.OrderDTO;
import com.eshopingzone.payload.OrderItemDTO;
import com.eshopingzone.repository.OrderItemRepository;
import com.eshopingzone.repository.OrderRepository;
import com.eshopingzone.repository.PaymentRepository;

import jakarta.transaction.Transactional;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private CartRepository cartRepo;
	
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private OrderItemRepository orderItemRepo;
	
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private PaymentRepository paymentRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CartService cartService;

	@Transactional
	@Override
	public OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId,
			String pgStatus, String pgResponseMessage) {
		
		Cart cart = cartRepo.findCartByEmail(emailId);
		if(cart == null) {
			throw new ResourceNotFoundException("Cart is emtpy");
		}
		
		Address address = addressRepo.findById(addressId);
		
		Order order = new Order();
		order.setEmail(emailId);
		order.setOrderDate(LocalDate.now());
		order.setTotalAmount(cart.getTotalPrice());
		order.setOrderStatus("order Accepted");
		order.setAddressId(addressId);
		
		Payment payment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage);
		payment.setOrder(order);
		payment = paymentRepo.save(payment);
		order.setPayment(payment);
		
		Order savedOrder = orderRepo.save(order);
		
		List<CartItem> cartItems = cart.getCartItems();
		if(cartItems.isEmpty()) {
			throw new ResourceNotFoundException("Cart is Empty");
		}
		
		List<OrderItem> orderItems = new ArrayList<>();
		for(CartItems cartitem : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setProductId(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setDiscount(cartItem.getDiscount());
			orderItem.setOrderedProductPrice(cartItem.getProductPrice());
			orderItem.setOrder(savedOrder);
			orderItems.add(orderItem);
		}
		orderItems = orderItemRepo.saveAll(orderItems);
		
		cart.getCartItems().forEach(item -> {
			int quantity = item.getQuantity();
			Product product = item.getProduct();
			cartService .deleteProductFromCart(cart.getCartId(), item.getProduct());
		});
		
		OrderDTO orderDto = modelMapper.map(savedOrder, OrderDTO.class);
		orderItems.forEach(item -> orderDto.getOrderItems().add(modelMapper.map(item, OrderItemDTO.class)));
		
		orderDto.setAddressId(addressId);
		
		return orderDto;
	}

}
