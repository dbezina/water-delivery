package com.bezina.water_delivery.notification_service.kafka;

import com.bezina.water_delivery.core.events.CourierAssignmentEvent;
import com.bezina.water_delivery.core.events.PaymentConfirmedEvent;
import com.bezina.water_delivery.core.events.PaymentFailedEvent;
import com.bezina.water_delivery.notification_service.rest.NotificationController;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationKafkaCourierListener {
    private final NotificationController notificationController;
    public NotificationKafkaCourierListener(NotificationController notificationController) {
        this.notificationController = notificationController;
    }

    @KafkaListener(
            topics = "courier.events",
            groupId = "notification-service",
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
        notificationController.sendMessageFromKafka(  Map.of(
                        "msg", "Order # "+  event.getOrderNo()+ " is ASSIGNED  to " +event.getCourierId()
                )
        );

    }


}
