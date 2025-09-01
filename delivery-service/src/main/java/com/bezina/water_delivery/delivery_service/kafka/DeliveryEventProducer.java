package com.bezina.water_delivery.delivery_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeliveryEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DeliveryEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendDeliveryEvent(Object event) {
        kafkaTemplate.send("delivery.events", UUID.randomUUID().toString(), event);
    }
}
