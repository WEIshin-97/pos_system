package com.snorlax.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http) throws Exception{
		
		return http.sessionManagement(management -> 
					management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				// if wrong or no JWT token, will access denied
				.authorizeHttpRequests( Authorize -> 
						//order is important
						Authorize.requestMatchers("/api/super-admin/**")
								.hasRole("ADMIN") // will check endpoint super-admin has role admin
								.requestMatchers("/api/**").authenticated() //will check authenticated where endpoint start with /api/** by validate the jwt
								.anyRequest().permitAll() //other endpoint just allow it pass by
						) 
				// check first (in JwtValidator class)
				.addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class)
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> 
						cors.configurationSource(corsConfigurationSource())
						)
				.build();
		
	}
	
	// if no this, frontend will not allow us to fetch data from our API
	// this tell browser this is authenticated resource or trusted website
	private CorsConfigurationSource corsConfigurationSource() {
		return new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
				
				CorsConfiguration cfg = new CorsConfiguration();
//				cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:5173"));
				cfg.setAllowedOrigins(Collections.singletonList("*"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setExposedHeaders(Collections.singletonList("Authorization"));
				cfg.setMaxAge(3600l);
				
				return cfg;
			}
		};
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}

}


