package com.bezina.water_delivery.notification_service.kafka;

import com.bezina.water_delivery.core.events.LowStockEvent;
import com.bezina.water_delivery.core.events.OrderConfirmedEvent;
import com.bezina.water_delivery.core.events.PaymentConfirmedEvent;
import com.bezina.water_delivery.core.events.PaymentFailedEvent;
import com.bezina.water_delivery.notification_service.rest.NotificationController;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationKafkaAdminListener {
    private final NotificationController notificationController;
    public NotificationKafkaAdminListener(NotificationController notificationController) {
        this.notificationController = notificationController;
    }

    @KafkaListener(
            topics = "orders.order_confirmed",
            groupId = "notification-service",
            containerFactory = "orderConfirmedKafkaListenerFactory"
    )
    public void handleConfirmedOrder(OrderConfirmedEvent event) {
        notificationController.sendMessageFromKafka(  Map.of(
                        "msg", "Order #  "+  event.getOrderNo()+
                                " is CONFIRMED and  ASSIGNED  to default courier " )
                );

        System.out.println("✅ Admin received confirmed order: " + event.getOrderNo());
    }
  /*  @KafkaListener(
            topics = "admin.low_stock",
            groupId = "notification-service",
            containerFactory = "lowStockKafkaListenerFactory")
    public void handleLowStockEvent ( LowStockEvent event) {

        notificationController.sendMessageFromKafka(  Map.of(
                "msg", "There are no more "+  event.getSize()+
                        " in stock ,please order more " )
        );

        System.out.println("✅ Admin received stockeLittleGoods : " + event.getSize());
    }*/

}
