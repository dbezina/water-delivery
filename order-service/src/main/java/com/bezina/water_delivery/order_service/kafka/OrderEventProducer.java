package com.bezina.water_delivery.order_service.kafka;

import com.bezina.water_delivery.core.events.OrderConfirmedEvent;
import com.bezina.water_delivery.core.events.OrderCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_CREATED = "orders.order_created";
    private static final String TOPIC_CONFIRMED = "orders.order_confirmed";

    public OrderEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send(TOPIC_CREATED, event.getOrderId(), event);
    }
    public void sendOrderConfirmedEvent(OrderConfirmedEvent event) {
        kafkaTemplate.send(TOPIC_CONFIRMED, event.getOrderId(), event);
    }

}