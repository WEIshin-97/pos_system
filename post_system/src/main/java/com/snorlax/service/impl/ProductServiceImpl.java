package com.snorlax.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.snorlax.mapper.ProductMapper;
import com.snorlax.modal.Product;
import com.snorlax.modal.Store;
import com.snorlax.modal.User;
import com.snorlax.payload.dto.ProductDto;
import com.snorlax.repository.ProductRepository;
import com.snorlax.repository.StoreRepository;
import com.snorlax.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepository;
	private final StoreRepository storeRepository;
	
	@Override
	public ProductDto createProduct(ProductDto productDto, User user) throws Exception {
		
		Store store = storeRepository.findById(productDto.getStoreId())
				.orElseThrow(() -> new Exception("Store not found"));
		
		Product product = ProductMapper.toEntity(productDto, store);
		Product savedProduct = productRepository.save(product);
		
		return ProductMapper.toDTO(savedProduct);
	}

	@Override
	public ProductDto updateProduct(Long id, ProductDto productDto, User user) throws Exception {
		
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new Exception("Product not found"));
		
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setSku(productDto.getSku());
		product.setImage(productDto.getImage());
		product.setMrp(productDto.getMrp());
		product.setSellingPrice(productDto.getSellingPrice());
		product.setBrand(productDto.getBrand());
		
		Product savedProduct = productRepository.save(product);
		
		return ProductMapper.toDTO(savedProduct);
	}

	@Override
	public void deleteProduct(Long id, User user) throws Exception {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new Exception("Product not found"));
		
		productRepository.delete(product);
		
	}

	@Override
	public List<ProductDto> getProductByStoreId(Long storeId) {
		List<Product> products = productRepository.findByStoreId(storeId);
		
		return products.stream()
				.map(ProductMapper::toDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<ProductDto> searchByKeyword(Long storeId, String keyword) {
		List<Product> products = productRepository.searchByKeyword(storeId, keyword);
		
		return products.stream()
				.map(ProductMapper::toDTO)
				.collect(Collectors.toList());
	}

}
