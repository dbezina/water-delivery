package com.bezina.water_delivery.delivery_service.kafka;

import com.bezina.water_delivery.core.events.IsDeliveredEvent;
import com.bezina.water_delivery.core.events.CourierAssignmentEvent;
import com.bezina.water_delivery.delivery_service.events.DeliveryAssignedEvent;
import com.bezina.water_delivery.delivery_service.events.DeliveryStatusChangedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeliveryEventProducer {
    private final String TOPIC_ORDER_DELIVERED = "delivery.events.order-delivered";
    private final String TOPIC_DELIVERY_STATUS_CHANGED = "delivery.events.status-changed";
    private final String TOPIC_DELIVERY_ASSIGNED = "courier.order-assigned";
    private final String TOPIC_COURIER_EVENTS = "courier.events";


    private final KafkaTemplate<String, Object> kafkaTemplate;

    public DeliveryEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendDeliveryEvent(String topic, Object event) {
        kafkaTemplate.send(topic, UUID.randomUUID().toString(), event);
    }

    public void sendCourierAssignmentEvent(CourierAssignmentEvent courierAssignmentEvent) {
        sendDeliveryEvent(TOPIC_COURIER_EVENTS,courierAssignmentEvent);
    }

    public void sendDeliveryAssignedEvent(DeliveryAssignedEvent deliveryAssignedEvent) {
        sendDeliveryEvent(TOPIC_DELIVERY_ASSIGNED,deliveryAssignedEvent );
    }

    public void sendDeliveryStatusChanged(DeliveryStatusChangedEvent event) {
        sendDeliveryEvent(TOPIC_DELIVERY_STATUS_CHANGED, event);
    }

    public void sendIsDeliveredStatusEvent(IsDeliveredEvent deliveredEvent ) {
        sendDeliveryEvent(TOPIC_ORDER_DELIVERED, deliveredEvent);
    }
}
