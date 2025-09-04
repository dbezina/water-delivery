package com.bezina.water_delivery.notification_service.kafka;

import com.bezina.water_delivery.core.events.CourierAssignmentEvent;
import com.bezina.water_delivery.core.events.OrderConfirmedEvent;
import com.bezina.water_delivery.notification_service.rest.NotificationController;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
public class NotificationKafkaListener {/*
    private final NotificationController notificationController;

    public NotificationKafkaListener(NotificationController notificationController) {
        this.notificationController = notificationController;
    }

    @KafkaListener(topics = "courier.events",
                   groupId = "notification-service")
    public void handleCourierAssignment(CourierAssignmentEvent event) {
        // уведомляем курьера, который получил задание
        notificationController.sendToUser(event.getCourierId(), Map.of(
                "type", "NEW_ORDER",
                "orderNo", event.getOrderNo(),
                "address", event.getAddress()
        ));
        System.out.println("📦 Sent new order notification to courier " + event.getCourierId());
    }

    @KafkaListener(
            topics = "orders.order_confirmed",
            groupId = "notification-service",
            containerFactory = "orderConfirmedKafkaListenerFactory")
    public void handleOrderConfirmed(OrderConfirmedEvent event) {
        // уведомляем пользователя, что заказ подтверждён
        notificationController.sendToUser(event.getUserId(), Map.of(
                "type", "ORDER_CONFIRMED",
                "orderNo", event.getOrderNo()
        ));
        System.out.println("✅ Sent order confirmed notification to user " + event.getUserId());
    }*/
}
