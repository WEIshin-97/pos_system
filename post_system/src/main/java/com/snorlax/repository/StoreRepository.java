package com.snorlax.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snorlax.modal.Store;

public interface StoreRepository extends JpaRepository<Store, Long>{

	Store findByStoreAdminId(Long adminId);
}
