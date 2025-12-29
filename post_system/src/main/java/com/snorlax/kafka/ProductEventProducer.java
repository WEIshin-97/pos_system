package com.snorlax.kafka;

import com.snorlax.event.ProductCreatedEvent;

import lombok.RequiredArgsConstructor;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventProducer {

    private static final String TOPIC = "product-created";

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public void send(ProductCreatedEvent event) {
        kafkaTemplate.send(TOPIC, event);
        System.out.println("Sent event: " + event);
    }
}
