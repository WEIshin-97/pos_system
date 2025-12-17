package com.snorlax.service;

import java.util.List;

import com.snorlax.modal.User;
import com.snorlax.payload.dto.ProductDto;

public interface ProductService {
	
	ProductDto createProduct(ProductDto productDto, User user) throws Exception;
	ProductDto updateProduct(Long id, ProductDto productDto, User user) throws Exception;
	void deleteProduct(Long id, User user) throws Exception;
	List<ProductDto> getProductByStoreId(Long storeId);
	List<ProductDto> searchByKeyword(Long storeId, String keyword);

}
