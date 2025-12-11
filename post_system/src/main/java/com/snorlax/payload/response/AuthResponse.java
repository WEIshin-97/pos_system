package com.snorlax.payload.response;

import com.snorlax.payload.dto.UserDto;

import lombok.Data;

@Data
public class AuthResponse {

	private String jwt;
	private String message;
	private UserDto user;
}
