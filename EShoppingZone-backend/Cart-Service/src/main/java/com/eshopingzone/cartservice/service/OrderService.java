package com.eshopingzone.cartservice.service;

import com.eshopingzone.cartservice.payload.OrderDTO;

public interface OrderService {

	OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId,
			String pgStatus, String pgResponseMessage);

}
