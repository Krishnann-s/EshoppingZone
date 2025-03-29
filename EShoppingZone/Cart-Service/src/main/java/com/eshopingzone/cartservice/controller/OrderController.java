package com.eshopingzone.cartservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshopingzone.cartservice.payload.OrderDTO;
import com.eshopingzone.cartservice.payload.OrderRequestDTO;
import com.eshopingzone.cartservice.service.OrderService;
import com.eshopingzone.cartservice.util.AuthUtil;


@RestController
@RequestMapping("/api")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@Autowired
	private AuthUtil authUtil;
	
	@PostMapping("/order/users/payments/{paymentMethod}")
	public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod,
			@RequestBody OrderRequestDTO orderRequestDto) {
		String emailId = authUtil.loggedInEmail();
		OrderDTO order = orderService.placeOrder(
				emailId, 
				orderRequestDto.getAddressId(),
				paymentMethod, 
				orderRequestDto.getPgName(),
				orderRequestDto.getPgPaymentId(),
				orderRequestDto.getPgStatus(),
				orderRequestDto.getPgResponseMessage());
		
		return new ResponseEntity<>(order, HttpStatus.OK);
	}
}
