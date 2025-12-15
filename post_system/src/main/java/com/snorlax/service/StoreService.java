package com.snorlax.service;

import java.util.List;

import com.snorlax.domain.StoreStatus;
import com.snorlax.exceptions.UserException;
import com.snorlax.modal.Store;
import com.snorlax.modal.User;
import com.snorlax.payload.dto.StoreDto;

public interface StoreService {
	
	StoreDto createStore(StoreDto storeDto, User user) throws Exception;
	StoreDto getStoreById(Long id) throws Exception;
	List<StoreDto> getAllStores();
	Store getStoreByAdmin() throws UserException;
	
	StoreDto updateStore(Long id, StoreDto storeDto) throws Exception;
	void deleteStore(Long id) throws UserException;
	StoreDto getStoreByEmployee() throws UserException;
	StoreDto moderateStore(Long id, StoreStatus status) throws Exception;
	
}
