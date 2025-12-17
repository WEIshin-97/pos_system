package com.snorlax.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snorlax.modal.User;
import com.snorlax.payload.dto.ProductDto;
import com.snorlax.payload.response.ApiResponse;
import com.snorlax.service.ProductService;
import com.snorlax.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {
	
	private final ProductService productService;
	private final UserService userService;
	
	@PostMapping()
	public ResponseEntity<ProductDto> createStore(
			@RequestBody ProductDto ProductDto,
			@RequestHeader ("Authorization") String jwt) 
		throws Exception{
		
		User user = userService.getUserFromJwtToken(jwt);

		return ResponseEntity.ok(productService.createProduct(ProductDto, user));
	}
	
	@GetMapping("/store/{id}")
	public ResponseEntity<List<ProductDto>> getByStoreById(
			@PathVariable Long id) 
		throws Exception{
		
		return ResponseEntity.ok(productService.getProductByStoreId(id));
	}
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<ProductDto> updateStore(
			@PathVariable Long id,
			@RequestBody ProductDto ProductDto,
			@RequestHeader ("Authorization") String jwt) 
		throws Exception{
		
		User user = userService.getUserFromJwtToken(jwt);
		
		return ResponseEntity.ok(productService.updateProduct(id, ProductDto, user));
	}
	
	@GetMapping("/store/{storeId}/search")
	public ResponseEntity<List<ProductDto>> searchByKeyword(
			@PathVariable Long storeId,
			@RequestParam String keyword) 
		throws Exception{
		
		return ResponseEntity.ok(productService.searchByKeyword(storeId, keyword));
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteStore(
			@PathVariable Long id,
			@RequestHeader ("Authorization") String jwt) 
		throws Exception{
		
		User user = userService.getUserFromJwtToken(jwt);
		
		productService.deleteProduct(id, user);
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setMessage("Product Delete Successfully");
		
		return ResponseEntity.ok(apiResponse);
	}

}
