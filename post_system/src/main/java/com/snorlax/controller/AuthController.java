package com.snorlax.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.snorlax.exceptions.UserException;
import com.snorlax.payload.dto.UserDto;
import com.snorlax.payload.response.AuthResponse;
import com.snorlax.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> signupHandler(
			@RequestBody UserDto userDto) throws UserException{
		
		return ResponseEntity.ok(authService.signUp(userDto));
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginHandler(
			@RequestBody UserDto userDto) throws UserException{
		
		return ResponseEntity.ok(authService.login(userDto));
		
	}

}
