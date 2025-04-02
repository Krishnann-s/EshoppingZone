package com.eshopingzone.cartservice.exception;

public class ResourceNotFoundException extends RuntimeException{

	public ResourceNotFoundException(String msg) {
		super(msg);
	}
}
