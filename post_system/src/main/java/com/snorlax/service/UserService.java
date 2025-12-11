package com.snorlax.service;

import java.util.List;

import com.snorlax.exceptions.UserException;
import com.snorlax.modal.User;

public interface UserService {
	
	User getUserFromJwtToken(String token) throws UserException;
	User getCurrentUser() throws UserException;
	User getUserByEmail(String email) throws UserException;
	User getUserById(Long id) throws UserException;
	List<User> getAllUsers();
	

}
