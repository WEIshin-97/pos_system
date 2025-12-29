package com.snorlax.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.snorlax.event.ProductCreatedEvent;

@Service
public class ProductNotificationConsumer {
	
	@KafkaListener(
			topics = "product-created",
			groupId = "notification-group"
			
	)
	public void handle(ProductCreatedEvent event) {
		
		System.out.println(
		          "Notify Manager " + event.getManagerId() +
		          ": Product created - " + event.getName()
		        );
		
	}

}
