package com.snorlax.modal;

import java.time.LocalDateTime;

import com.snorlax.domain.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Entity
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String fullname;
	
	@Column(nullable = false, unique = true)
	@Email(message = "Email should be valid")
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private String phone;
	
	@Column(nullable = false)
	private UserRole role;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private LocalDateTime lastLogin;
	
}
