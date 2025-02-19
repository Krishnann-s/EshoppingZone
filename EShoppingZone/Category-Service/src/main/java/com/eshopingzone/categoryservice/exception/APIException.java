package com.eshopingzone.categoryservice.exception;

public class APIException extends RuntimeException{
	
	private static final Long serailVersionUID = 1L;
	
	public APIException() {
		
	}
	
	public APIException(String msg) {
		super(msg);
	}
}
