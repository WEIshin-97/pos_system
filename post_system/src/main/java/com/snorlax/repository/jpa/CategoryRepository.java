package com.snorlax.repository.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snorlax.modal.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{
	
	List<Category> findByStoreId(Long storeId);

}
