package com.bezina.water_delivery.delivery_service.service;

import com.bezina.water_delivery.core.model.Assignment;
import com.bezina.water_delivery.core.events.IsDeliveredEvent;
import com.bezina.water_delivery.delivery_service.kafka.DeliveryEventProducer;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Service
public class UpdateMessages {
        private final DeliveryEventProducer eventProducer;

        public UpdateMessages(DeliveryEventProducer eventProducer) {
                this.eventProducer = eventProducer;
        }

        public void sendIsDeliveredMessageToKafka(Assignment assignment){
    // публикуем событие
        eventProducer.sendIsDeliveredStatusEvent(new IsDeliveredEvent(
                assignment.getOrderNo(),
                Instant.now().toEpochMilli()
        ));
        System.out.println("DeliveryStatusChangedEvent saved"+assignment.getOrderNo()+" "+ assignment.getStatus());
        }
}
