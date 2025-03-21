package com.eshopingzone.profileservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetails {

	 private int statusCode;
	 private String message;
	 private String details;
}
