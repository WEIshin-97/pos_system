package com.snorlax.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snorlax.domain.StoreStatus;
import com.snorlax.exceptions.UserException;
import com.snorlax.mapper.StoreMapper;
import com.snorlax.modal.User;
import com.snorlax.payload.dto.StoreDto;
import com.snorlax.payload.response.ApiResponse;
import com.snorlax.service.StoreService;
import com.snorlax.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {
	
	private final StoreService storeService;
	private final UserService userService;
	
	@PostMapping()
	public ResponseEntity<StoreDto> createStore(
			@RequestBody StoreDto storeDto,
			@RequestHeader ("Authorization") String jwt) 
		throws Exception{
		
		User user = userService.getUserFromJwtToken(jwt);
		
		return ResponseEntity.ok(storeService.createStore(storeDto, user));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StoreDto> getStoreById(
			@PathVariable Long id) 
		throws Exception{
		
		
		return ResponseEntity.ok(storeService.getStoreById(id));
	}
	
	@GetMapping()
	public ResponseEntity<List<StoreDto>> getAllStore() 
		throws Exception{
		
		return ResponseEntity.ok(storeService.getAllStores());
	}
	
	@GetMapping("/admin")
	public ResponseEntity<StoreDto> getStoreByAdmin() 
		throws Exception{
		
		return ResponseEntity.ok(StoreMapper.toDto(storeService.getStoreByAdmin()));
	}
	
	@GetMapping("/employee")
	public ResponseEntity<StoreDto> getStoreByEmployee() 
		throws Exception{
		
		return ResponseEntity.ok(storeService.getStoreByEmployee());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<StoreDto> updateStore(
			@PathVariable Long id,
			@RequestBody StoreDto storeDto) 
		throws Exception{
		
		return ResponseEntity.ok(storeService.updateStore(id, storeDto));
	}
	
	@PutMapping("/{id}/moderate")
	public ResponseEntity<StoreDto> moderateStore(
			@PathVariable Long id,
			@RequestParam StoreStatus status) 
		throws Exception{
		
		return ResponseEntity.ok(storeService.moderateStore(id, status));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteStore(
			@PathVariable Long id) 
		throws Exception{
		
		storeService.deleteStore(id);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Store Delete Successfully");
		
		return ResponseEntity.ok(apiResponse);
	}
	
	
	

}
