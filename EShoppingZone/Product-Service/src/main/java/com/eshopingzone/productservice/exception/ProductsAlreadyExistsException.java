package com.eshopingzone.productservice.exception;

public class ProductsAlreadyExistsException extends RuntimeException{

	public ProductsAlreadyExistsException(String msg) {
		super(msg);
	}
}
