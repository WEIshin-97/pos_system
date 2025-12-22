package com.snorlax.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.snorlax.domain.StoreStatus;
import com.snorlax.domain.UserRole;
import com.snorlax.exceptions.UserException;
import com.snorlax.mapper.StoreMapper;
import com.snorlax.modal.Store;
import com.snorlax.modal.StoreContact;
import com.snorlax.modal.User;
import com.snorlax.payload.dto.StoreDto;
import com.snorlax.repository.jpa.StoreRepository;
import com.snorlax.service.StoreService;
import com.snorlax.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{
	
	private final StoreRepository storeRepository;
	private final UserService userService;
	
	
	@Override
	public StoreDto createStore(StoreDto storeDto, User user) throws Exception {
		
		if(!user.getRole().equals(UserRole.ROLE_STORE_ADMIN)) {
			throw new Exception("Only Store Admin allow to create store");
		}
		
		// Prevent one admin creating multiple stores
        Store existing = storeRepository.findByStoreAdminId(user.getId());
        if (existing != null) {
            throw new RuntimeException("Store already exists for this admin");
        }
	
		//convert dto to entity
		Store store = StoreMapper.toEntity(storeDto, user);
		
		//@PrePersist runs automatically
		return StoreMapper.toDto(storeRepository.save(store));
	}

	@Override
	public StoreDto getStoreById(Long id) throws Exception {
		
		Store store = storeRepository.findById(id).orElseThrow(
				() -> new Exception("Store not found...")
		);
		return StoreMapper.toDto(store);
	}

	@Override
	public List<StoreDto> getAllStores() {
		List<Store> dtos = storeRepository.findAll();
		
		return dtos.stream().map(StoreMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public Store getStoreByAdmin() throws UserException {
		
		User admin = userService.getCurrentUser();
		if (admin.getRole() != UserRole.ROLE_STORE_ADMIN) {
            throw new UserException("Not a store admin");
        }
		
		Store store = storeRepository.findByStoreAdminId(admin.getId());
        if (store == null) {
            throw new UserException("Store not found for admin");
        }
		
		return store;
	}

	@Override
	public StoreDto updateStore(Long id, StoreDto storeDto) throws Exception {
		
		User admin = userService.getCurrentUser();
		
		Store existing = storeRepository.findByStoreAdminId(admin.getId());
		
		if(existing == null) {
			throw new Exception("Store not found");
		}
		
		existing.setBrand(storeDto.getBrand());
		existing.setDescription(storeDto.getDescription());
		
		if(storeDto.getStoreType() != null) {
			existing.setStoreType(storeDto.getStoreType());
		}
		
		if(storeDto.getContact() != null) {
			StoreContact contact = StoreContact.builder()
					.address(storeDto.getContact().getAddress())
					.phone(storeDto.getContact().getPhone())
					.email(storeDto.getContact().getEmail())
					.build();
			existing.setContact(contact);
		}
		
		Store updatedStore = storeRepository.save(existing);
		
		return StoreMapper.toDto(updatedStore);
	}

	@Override
	public void deleteStore(Long id) throws UserException {
		
		Store store = getStoreByAdmin();
		storeRepository.delete(store);
	}

	@Override
	public StoreDto getStoreByEmployee() throws UserException {
		
		User currentUser = userService.getCurrentUser();
		if(currentUser == null) {
			throw new UserException("You do not have permission to access this store");
		}
		
		return StoreMapper.toDto(currentUser.getStore());
	}

	@Override
	public StoreDto moderateStore(Long id, StoreStatus status) throws Exception {
		
		Store store = storeRepository.findById(id).orElseThrow(
				() -> new Exception("Store not found...")
		);
		
		store.setStatus(status);
		Store updatedStore = storeRepository.save(store);
		
		return StoreMapper.toDto(updatedStore);
	}

}
