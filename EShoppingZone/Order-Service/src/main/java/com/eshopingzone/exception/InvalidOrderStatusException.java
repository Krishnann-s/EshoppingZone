package com.eshopingzone.exception;

public class InvalidOrderStatusException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidOrderStatusException(String message) {
        super(message);
    }
}