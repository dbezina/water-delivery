package com.bezina.water_delivery.delivery_service.kafka;

import com.bezina.water_delivery.delivery_service.events.CourierAssignmentEvent;
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
        kafkaTemplate.send("delivery.events.status-changed", UUID.randomUUID().toString(), event);
    }

    public void sendCourierAssignmentEvent(CourierAssignmentEvent courierAssignmentEvent) {
        kafkaTemplate.send("courier.events", UUID.randomUUID().toString(), courierAssignmentEvent);
    }
}
