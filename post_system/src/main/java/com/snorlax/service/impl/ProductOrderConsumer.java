package com.snorlax.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.snorlax.event.ProductCreatedEvent;

@Service
public class ProductOrderConsumer {
	
	@KafkaListener(
			topics = "product-created",
			groupId = "order-group"
			
	)
	public void handle(ProductCreatedEvent event) {
		
		System.out.println(
		          "Create order for Product ID: " + event.getProductId()
		        );
		
	}

}
