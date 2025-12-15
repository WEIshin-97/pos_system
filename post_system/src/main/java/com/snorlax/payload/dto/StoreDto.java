package com.snorlax.payload.dto;

import java.time.LocalDateTime;

import com.snorlax.domain.StoreStatus;
import com.snorlax.modal.StoreContact;

import lombok.Data;

@Data
public class StoreDto { //Used for API requests & responses

	private Long id;

	private String brand;

	private UserDto storeAdmin;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	private String description;
	
	private String storeType;
	
	private StoreStatus status;
	
	private StoreContact contact;
}
