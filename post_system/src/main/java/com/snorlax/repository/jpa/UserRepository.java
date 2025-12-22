package com.snorlax.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.snorlax.modal.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByEmail(String email);

}
