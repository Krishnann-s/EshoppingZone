package com.eshopingzone.service;

import com.eshopingzone.payload.OrderDTO;

public interface OrderService {

	OrderDTO placeOrder(String emailId, Long addressId, String paymentMethod, String pgName, String pgPaymentId,
			String pgStatus, String pgResponseMessage);

}
