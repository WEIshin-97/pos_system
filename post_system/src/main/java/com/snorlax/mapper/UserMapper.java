package com.snorlax.mapper;

import com.snorlax.modal.User;
import com.snorlax.payload.dto.UserDto;

public class UserMapper {

	public static UserDto toDto(User savedUser) {
		
		UserDto userDto = new UserDto();
		userDto.setId(savedUser.getId());
		userDto.setEmail(savedUser.getEmail());
		userDto.setRole(savedUser.getRole());
		userDto.setFullname(savedUser.getFullname());
		userDto.setPhone(savedUser.getPhone());
		userDto.setLastLogin(savedUser.getLastLogin());
		userDto.setCreatedAt(savedUser.getCreatedAt());
		userDto.setUpdatedAt(savedUser.getUpdatedAt());
		
		return userDto;
	}

}
