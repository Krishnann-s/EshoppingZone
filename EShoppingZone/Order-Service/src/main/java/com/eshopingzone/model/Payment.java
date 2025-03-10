package com.eshopingzone.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;
	
	@OneToOne(mappedBy = "payment", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Order order;
	
	@NotBlank
	@Size(min = 4, message = "Payment method must contain at least 4 characters")
	private String paymentMethod;

	private String pgPaymentId; // Payment Gateway 
	
	private String pgStatus;
	
	private String pgResponseMessage;
	
	private String pgName;
	
	public Payment(String paymentMethod, String pgPaymentId, String pgStatus, String pgResponseMessage) {
		this.paymentMethod = paymentMethod;
		this.pgPaymentId = pgPaymentId;
		this.pgStatus = pgStatus;
		this.pgResponseMessage = pgResponseMessage;
	}
}
