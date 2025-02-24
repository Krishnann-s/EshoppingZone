package com.eshopingzone.productservice.exception;

public class CategoryNotFoundException extends RuntimeException{

	public CategoryNotFoundException(String msg) {
		super(msg);
	}
}
