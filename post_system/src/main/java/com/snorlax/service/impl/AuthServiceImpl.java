package com.snorlax.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.snorlax.config.JwtProvider;
import com.snorlax.domain.UserRole;
import com.snorlax.exceptions.UserException;
import com.snorlax.mapper.UserMapper;
import com.snorlax.modal.User;
import com.snorlax.payload.dto.UserDto;
import com.snorlax.payload.response.AuthResponse;
import com.snorlax.repository.UserRepository;
import com.snorlax.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;
	private final CustomUserServiceImpl customUserServiceImpl;

	@Override
	public AuthResponse signUp(UserDto userDto) throws UserException {
		
		User user = userRepository.findByEmail(userDto.getEmail());
		
		if(user != null) {
			throw new UserException("email is already registered!");
		}
		
		if(userDto.getRole().equals(UserRole.ROLE_ADMIN)) {
			throw new UserException("role admin is not allowed!");
		}
		
		User newUser = new User();
		newUser.setEmail(userDto.getEmail());
		newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
		newUser.setRole(userDto.getRole());
		newUser.setFullname(userDto.getFullname());
		newUser.setPhone(userDto.getPhone());
		newUser.setLastLogin(userDto.getLastLogin());
		newUser.setCreatedAt(userDto.getCreatedAt());
		newUser.setUpdatedAt(userDto.getUpdatedAt());
		
		User savedUser = userRepository.save(newUser);
		
		Authentication authentication = 
				new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
		
		// new UsernamePasswordAuthenticationToken(principal, credentials)
		// principal → something that represents the user (here: email)
		// credentials → the raw password
		// no authorities provided, so authorities = null
		// During sign-up, you do not verify the password, this constructor trigger authenticated = false

		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateToken(authentication);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Registered successfully");
		authResponse.setUser(UserMapper.toDto(savedUser));
		
		return authResponse;
	}

	@Override
	public AuthResponse login(UserDto userDto) throws UserException {
		
		String email = userDto.getEmail();
		String password = userDto.getPassword();
		
		// from private Authentication authenticate method
		Authentication authentication = authenticate(email, password);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		String role = authorities.iterator().next().getAuthority();
		
		String jwt = jwtProvider.generateToken(authentication);
		
		User user = userRepository.findByEmail(userDto.getEmail());
		user.setLastLogin(LocalDateTime.now());
		userRepository.save(user);
		
		AuthResponse authResponse = new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login successfully");
		authResponse.setUser(UserMapper.toDto(user));
		

		return authResponse;
	}

	private Authentication authenticate(String email, String password) throws UserException {
		
		UserDetails userDetails = customUserServiceImpl.loadUserByUsername(email);
		
		if(userDetails == null) {
			throw new UserException("email does not exist: " + email);
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new UserException("Password does not match");
		}
		
		//new UsernamePasswordAuthenticationToken(principal, credentials, authorities)
		//principal → the actual UserDetails object (email, password, roles)
		//credentials → null because we don’t store raw password after authentication
		//authorities → ROLE_USER / ROLE_ADMIN etc.
		//After verify password, this constructor trigger authenticated = true
		return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	}

}
