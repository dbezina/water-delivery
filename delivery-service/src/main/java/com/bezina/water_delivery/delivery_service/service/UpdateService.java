package com.bezina.water_delivery.delivery_service.service;

import com.bezina.water_delivery.core.events.DeliveryStatusChangedEvent;
import com.bezina.water_delivery.core.model.assignment.Assignment;
import com.bezina.water_delivery.core.events.IsDeliveredEvent;
import com.bezina.water_delivery.delivery_service.kafka.DeliveryEventProducer;
import org.springframework.stereotype.Service;

import java.time.Instant;
@Service
public class UpdateService {
        private final DeliveryEventProducer eventProducer;

        public UpdateService(DeliveryEventProducer eventProducer) {
                this.eventProducer = eventProducer;
        }

        public void sendIsDeliveredMessageToKafka(Assignment assignment){
    // публикуем событие IsDelivered
        eventProducer.sendIsDeliveredStatusEvent(new IsDeliveredEvent(
                assignment.getOrderNo(),
                assignment.getStatus(),
                Instant.now().toEpochMilli()
        ));
                System.out.println("DeliveryStatusChangedEvent saved"+
                        assignment.getOrderNo()+" "+ assignment.getStatus());
        }

        public void sendIsStatusChangedToKafka(Assignment assignment){
                // публикуем событие IsDelivered
                eventProducer.sendDeliveryStatusChanged(new DeliveryStatusChangedEvent(
                        assignment.getOrderNo(),
                        assignment.getStatus(),
                        Instant.now().toEpochMilli()
                ));
                System.out.println("DeliveryStatusChangedEvent saved"+assignment.getOrderNo()+
                        " "+ assignment.getStatus());
        }
}
