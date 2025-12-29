package com.snorlax.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreatedEvent { //Event is for communication
	
	private Long productId;
	private String name;
	
	private Long categoryId;
	private Long storeId;
	
	private Long managerId; //notify who

}
