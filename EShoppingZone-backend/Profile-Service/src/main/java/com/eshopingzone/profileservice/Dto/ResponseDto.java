package com.eshopingzone.profileservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
	
	private String token;
	private String role;
	private String message;

	public ResponseDto(String message) {
		this.message = message;
	}
}
