package com.bezina.water_delivery.delivery_service.kafka;

import com.bezina.water_delivery.delivery_service.events.CourierAssignmentEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class DeliveryEventListener {

    @KafkaListener(
            topics = "courier.events",
            groupId = "courier-service",
            containerFactory = "courierEventKafkaListenerFactory"
    )
    public void handleCourierAssignmentEvent(CourierAssignmentEvent event) {
        System.out.println("📦 Новый заказ для курьера " + event.getCourierId() +
                " | OrderNo: " + event.getOrderNo() +
                " | Адрес: " + event.getAddress() +
                " | Клиент: " + event.getUserId() +
                " | Items: " + event.getItems().stream()
                .map(i -> i.getSize() + " x" + i.getQuantity())
                .toList());
    }
}
