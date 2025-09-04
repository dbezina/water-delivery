package com.bezina.water_delivery.notification_service.kafka;

import com.bezina.water_delivery.core.events.*;
import com.bezina.water_delivery.core.model.enums.OrderStatus;
import com.bezina.water_delivery.notification_service.rest.NotificationController;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationKafkaUserListener {
    private final NotificationController notificationController;
    public NotificationKafkaUserListener(NotificationController notificationController) {
        this.notificationController = notificationController;
    }

    @KafkaListener(
            topics = "payment.confirmed",
            groupId = "notification-service",
            containerFactory = "paymentConfirmedKafkaListenerFactory"
    )
    public void handleOrderConfirmed(PaymentConfirmedEvent event) {
        System.out.println("notification-service orders.order_confirmed");
        // уведомляем пользователя, что заказ подтверждён
        notificationController.sendMessageFromKafka(
                Map.of(
                        "msg", "Order # "+  event.getOrderId()+ " is CONFIRMED now"
                )

        );
        System.out.println("✅ Sent order confirmed notification to user " + event.toString());
    }

    @KafkaListener(
            topics = "payment.failed",
            groupId = "notification-service",
            containerFactory = "paymentFailedKafkaListenerFactory"
          )
    public void handlePaymentFailed(PaymentFailedEvent event) {
        System.out.println("notification-service PAYMENT_FAILED");
        // уведомляем пользователя, что заказ failed
        notificationController.sendMessageFromKafka(  Map.of(
                "msg", "Order # "+  event.getOrderId()+ " is CANCELlED now due to " +event.getReason()
                    )
                );
        System.out.println("❌ Sent PAYMENT_FAILED notification to user " + event.toString());
    }
    @KafkaListener(
            topics = "delivery.events.status-changed",
            groupId = "notification-service",
            containerFactory = "deliveryStatusKafkaListenerFactory"
    )
    public void handleDeliveryStatusChanged(DeliveryStatusChangedEvent event) {

        System.out.println("📦 Delivery status update: " + event);
        notificationController.sendMessageFromKafka(  Map.of(
                        "msg", "Order # "+  event.getOrderNo()+
                        " status is changed to " +event.getStatus()
                )
        );
    }
    @KafkaListener(
            topics = "delivery.events.order-delivered",
            groupId = "notification-service",
            containerFactory = "isDeliveredKafkaListenerFactory"
    )
    public void handleDelivered(IsDeliveredEvent event) {

        System.out.println("📦 Order delivered: " + event);
        notificationController.sendMessageFromKafka(  Map.of(
                        "msg", "Order # "+  event.getOrderNo()+
                                "status is changed to delivered " +event.getStatus()
                )
        );
    }

    @KafkaListener(
            topics = "inventory.stock_insufficient",
            groupId = "notification-service",
            containerFactory = "stockInsufficientKafkaListenerFactory"
    )
    public void handleStockInsufficientEvent(StockInsufficientEvent event) {
        System.out.println(" Order cancelled due to insufficient quantity: " + event.getOrderNo());
        notificationController.sendMessageFromKafka(  Map.of(
                        "msg", "Order # "+  event.getOrderNo()+
                                "cancelled due to  " +event.getReason()
                )
        );
    }

}
