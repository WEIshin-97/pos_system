package com.snorlax.mapper;

import com.snorlax.modal.Product;
import com.snorlax.modal.Store;
import com.snorlax.payload.dto.ProductDto;

public class ProductMapper {
	
	public static ProductDto toDTO(Product product) {
		
		return ProductDto.builder()
				.id(product.getId())
				.name(product.getName())
				.sku(product.getSku())
				.description(product.getDescription())
				.mrp(product.getMrp())
				.sellingPrice(product.getSellingPrice())
				.brand(product.getBrand())
				.storeId(product.getStore() != null ? product.getStore().getId() : null)
				.image(product.getImage())
				.createdAt(product.getCreatedAt())
				.updatedAt(product.getUpdatedAt())
				.build();	
	}
	
	//Both methods convert entities to DTOs. 
	//The Product mapper uses the Builder pattern for safer, more readable, and immutable object creation, 
	//while the Store mapper uses setters, which is simpler but more error-prone. 
	//Builder is preferred for API response DTOs.
	
	
	public static Product toEntity(ProductDto dto, Store store) {

	    Product product = new Product();
	    product.setName(dto.getName());
	    product.setSku(dto.getSku());
	    product.setDescription(dto.getDescription());
	    product.setMrp(dto.getMrp());
	    product.setSellingPrice(dto.getSellingPrice());
	    product.setBrand(dto.getBrand());
//	    product.setImage(dto.getImage());
//	    product.setStore(store);

	    return product;
	}


}
