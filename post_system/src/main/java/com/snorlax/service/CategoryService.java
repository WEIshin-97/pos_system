package com.snorlax.service;

import java.util.List;

import com.snorlax.payload.dto.CategoryDto;

public interface CategoryService {
	
	CategoryDto createCategory(CategoryDto dto) throws Exception;
	List<CategoryDto> getCategoriesByStore(Long storeId);
	CategoryDto updateCategory(Long id, CategoryDto dto) throws Exception;
	void deleteCategory(Long id) throws Exception;

}
