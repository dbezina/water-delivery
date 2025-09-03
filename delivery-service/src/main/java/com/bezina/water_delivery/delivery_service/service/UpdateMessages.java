package com.bezina.water_delivery.delivery_service.service;

import com.bezina.water_delivery.delivery_service.entity.Assignment;
import com.bezina.water_delivery.delivery_service.events.DeliveryStatusChangedEvent;
import com.bezina.water_delivery.delivery_service.kafka.DeliveryEventProducer;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Service
public class UpdateMessages {
        private final DeliveryEventProducer eventProducer;

        public UpdateMessages(DeliveryEventProducer eventProducer) {
                this.eventProducer = eventProducer;
        }

        public void sendUpdateStatusMessageToKafka(Assignment assignment){
    // публикуем событие
        eventProducer.sendDeliveryEvent(new DeliveryStatusChangedEvent(
                assignment.getOrderNo(), assignment.getStatus(),
                Instant.now().toEpochMilli()
        ));
        System.out.println("DeliveryStatusChangedEvent saved"+assignment.getOrderNo()+" "+ assignment.getStatus());
        }
}
