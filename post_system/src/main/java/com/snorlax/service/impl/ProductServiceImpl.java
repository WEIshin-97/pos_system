package com.snorlax.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.snorlax.event.ProductCreatedEvent;
import com.snorlax.kafka.ProductEventProducer;
import com.snorlax.mapper.ProductMapper;
import com.snorlax.modal.Category;
import com.snorlax.modal.Product;
import com.snorlax.modal.Store;
import com.snorlax.modal.User;
import com.snorlax.payload.dto.ProductDto;
import com.snorlax.repository.jpa.CategoryRepository;
import com.snorlax.repository.jpa.ProductRepository;
import com.snorlax.repository.jpa.StoreRepository;
import com.snorlax.repository.mybatis.ProductMyBatisMapper;
import com.snorlax.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional //Writing to DB (save / update / delete) | Multiple DB calls in one method | Mixing JPA + MyBatis | Business logic, not just read-only
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	
	private final ProductRepository productRepository;
	private final ProductMyBatisMapper productMyBatisMapper;
	private final StoreRepository storeRepository;
	private final CategoryRepository categoryRepository;
	private final ProductEventProducer productEventProducer;
	
	@Override
	public ProductDto createProduct(ProductDto productDto, User user) throws Exception {
		
		Store store = storeRepository.findById(productDto.getStoreId())
				.orElseThrow(() -> new Exception("Store not found"));
		
		Category category = categoryRepository.findById(productDto.getCategoryId())
				.orElseThrow(() -> new Exception("Category not found"));
		
		Product product = ProductMapper.toEntity(productDto, store, category);
		Product savedProduct = productRepository.save(product);
		
		// Publish Event
		Long managerId = (long) 52; //temporary
		
		ProductCreatedEvent event = new ProductCreatedEvent(
				savedProduct.getId(),
				savedProduct.getName(),
				savedProduct.getCategory().getId(),
				savedProduct.getStore().getId(),
				managerId
		);
		
		productEventProducer.send(event);
				
		
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
		
		if(productDto.getCategoryId() != null) {
			Category category = categoryRepository.findById(productDto.getCategoryId())
					.orElseThrow(() -> new Exception("Category not found"));
			
			product.setCategory(category);
		}
		
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
		List<Product> products = productMyBatisMapper.searchByKeyword(storeId, keyword);
		
		return products.stream()
				.map(ProductMapper::toDTO)
				.collect(Collectors.toList());
	}

}
