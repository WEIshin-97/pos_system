package com.snorlax.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryDto {
	
	private Long id;

	private String name;
	
	private Long storeId;

}
