package com.bezina.water_delivery.order_service.kafka;

import com.bezina.water_delivery.core.events.DeliveryStatusChangedEvent;
import com.bezina.water_delivery.core.events.IsDeliveredEvent;
import com.bezina.water_delivery.core.model.enums.OrderStatus;
import com.bezina.water_delivery.order_service.DAO.OrderStatusHistoryRepository;
import com.bezina.water_delivery.order_service.DAO.OrderRepository;
import com.bezina.water_delivery.order_service.services.OrderService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class DeliveryEventListener {
    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;
    private final OrderService orderService;

    public DeliveryEventListener(OrderRepository orderRepository, OrderStatusHistoryRepository historyRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
        this.orderService = orderService;
    }

    @KafkaListener(
            topics = "delivery.events.order-delivered",
            groupId = "order-service",
            containerFactory = "isDeliveredKafkaListenerFactory"
    )
    public void handleDelivered(IsDeliveredEvent event) {

        orderService.updateStatusFromDelivery(event.getOrderNo(), (OrderStatus.valueOf( event.getStatus().name())));
        System.out.println("📦 Order delivered: " + event);
    }

    @KafkaListener(
            topics = "delivery.events.status-changed",
            groupId = "order-service",
            containerFactory = "deliveryStatusKafkaListenerFactory"
    )
    public void handleDeliveryStatusChanged(DeliveryStatusChangedEvent event) {

        orderService.updateStatusFromDelivery(event.getOrderNo(), event.getStatus());
        System.out.println("📦 Delivery status update: " + event);

    }
}
