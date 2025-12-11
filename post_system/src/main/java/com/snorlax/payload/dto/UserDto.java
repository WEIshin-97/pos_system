package com.snorlax.payload.dto;

import java.time.LocalDateTime;

import com.snorlax.domain.UserRole;

import lombok.Data;

@Data
public class UserDto {

	private Long id;
	
	private String fullname;
	
	private String email;
	
	private String phone;
	
	private String password; //make the password null
	
	private UserRole role;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime lastLogin;
}
