package com.eshopingzone.categoryservice.exception;

public class CategoryNotFoundException extends RuntimeException{

	public CategoryNotFoundException(String msg) {
		super(msg);
	}
}
