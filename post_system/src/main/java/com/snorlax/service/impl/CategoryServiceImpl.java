package com.snorlax.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.snorlax.domain.UserRole;
import com.snorlax.mapper.CategoryMapper;
import com.snorlax.modal.Category;
import com.snorlax.modal.Store;
import com.snorlax.modal.User;
import com.snorlax.payload.dto.CategoryDto;
import com.snorlax.repository.jpa.CategoryRepository;
import com.snorlax.repository.jpa.StoreRepository;
import com.snorlax.service.CategoryService;
import com.snorlax.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final CategoryRepository categoryRepository;
	private final UserService userService;
	private final StoreRepository storeRepository;
	
	@Override
	public CategoryDto createCategory(CategoryDto dto) throws Exception {
		
		User user = userService.getCurrentUser();
		
		Store store = storeRepository.findById(dto.getStoreId()).orElseThrow(
				() -> new Exception("Store not found"));
		
		checkAuthority(user, store);
		
		Category category = new Category();
		category.setName(dto.getName());
		category.setStore(store);
		
		Category savedCategory = categoryRepository.save(category);
		
		return CategoryMapper.toDto(savedCategory);
	}

	@Override
	public List<CategoryDto> getCategoriesByStore(Long storeId) {
		
		List<Category> categories = categoryRepository.findByStoreId(storeId);
		
		return categories.stream().map(CategoryMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public CategoryDto updateCategory(Long id, CategoryDto dto) throws Exception {
		
		Category category = categoryRepository.findById(id).orElseThrow(
				() -> new Exception("category not found"));
		
		User user = userService.getCurrentUser();
		
		checkAuthority(user, category.getStore());
		
		category.setName(dto.getName());
		
		return CategoryMapper.toDto(categoryRepository.save(category));
	}

	@Override
	public void deleteCategory(Long id) throws Exception {
		Category category = categoryRepository.findById(id).orElseThrow(
				() -> new Exception("category not found"));
		
		User user = userService.getCurrentUser();
		
		checkAuthority(user, category.getStore());
		
		categoryRepository.delete(category);
		
	}
	
	private void checkAuthority(User user, Store store) throws Exception {
		
		boolean isAdmin = user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
		boolean isManager = user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
		boolean isSameStore = user.equals(store.getStoreAdmin());
		
		if(!(isAdmin && isSameStore) && !isManager) {
			throw new Exception("you do not have permission to manage this category");
		}
	}

}
