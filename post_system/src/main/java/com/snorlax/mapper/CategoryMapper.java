package com.snorlax.mapper;

import com.snorlax.modal.Category;
import com.snorlax.payload.dto.CategoryDto;

public class CategoryMapper {
	
	public static CategoryDto toDto(Category category) {
		
		// use when search product by store id not category id
		if (category == null) {
            return null; // safe return for null category
        }
		
		return CategoryDto.builder()
				.id(category.getId())
				.name(category.getName())
				.storeId(category.getStore() != null ? category.getStore().getId() : null)
				.build();
		
	}

}
