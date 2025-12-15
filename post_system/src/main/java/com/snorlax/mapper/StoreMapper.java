package com.snorlax.mapper;

import com.snorlax.modal.Store;
import com.snorlax.modal.User;
import com.snorlax.payload.dto.StoreDto;

public class StoreMapper {
	
	//Used when returning data to client
	public static StoreDto toDto(Store store) {
		
		StoreDto storeDto = new StoreDto();
		storeDto.setId(store.getId());
		storeDto.setBrand(store.getBrand());
		storeDto.setDescription(store.getDescription());
		//Converts User entity ➜ UserDto, prevent 1. infinite JSON recursion 2.password exposure
		storeDto.setStoreAdmin(UserMapper.toDto(store.getStoreAdmin()));
		storeDto.setStoreType(store.getStoreType());
		//@Embeddable, no sensitive data, safe to expose directly
		storeDto.setContact(store.getContact());
		storeDto.setCreatedAt(store.getCreatedAt());
		storeDto.setUpdatedAt(store.getUpdatedAt());
		storeDto.setStatus(store.getStatus());
		
		return storeDto;
		
	}
	
	//Used when: Creating | Updating : Converts client input into a JPA entity
	public static Store toEntity(StoreDto storeDto, User storeAdmin) {
		
		Store store = new Store();
		store.setId(storeDto.getId());
		store.setBrand(storeDto.getBrand());
		store.setDescription(storeDto.getDescription());
		
		//User entity is resolved in Service layer, DTO does NOT control relationships
		//Prevents privilege escalation
		store.setStoreAdmin(storeAdmin);
		store.setStoreType(storeDto.getStoreType());
		store.setContact(storeDto.getContact());
		
		// not recommend: controlled by JPA lifecycle (@PrePersist, @PreUpdate
		// store.setCreatedAt(storeDto.getCreatedAt());
		// store.setUpdatedAt(storeDto.getUpdatedAt());
		
		// store.setStatus(storeDto.getStatus());
		
		return store;
		
	}

}
