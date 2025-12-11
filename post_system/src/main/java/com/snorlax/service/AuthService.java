package com.snorlax.service;

import com.snorlax.exceptions.UserException;
import com.snorlax.payload.dto.UserDto;
import com.snorlax.payload.response.AuthResponse;

public interface AuthService {
	
	AuthResponse signUp(UserDto userDto) throws UserException;
	AuthResponse login(UserDto userDto) throws UserException;

}
