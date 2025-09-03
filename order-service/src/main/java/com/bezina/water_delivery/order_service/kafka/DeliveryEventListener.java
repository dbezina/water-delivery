package com.bezina.water_delivery.order_service.kafka;

import com.bezina.water_delivery.core.events.DeliveryStatusChangedEvent;
import com.bezina.water_delivery.core.events.IsDeliveredEvent;
import com.bezina.water_delivery.order_service.DAO.OrderStatusHistoryRepository;
import com.bezina.water_delivery.core.model.OrderStatusHistory;
import com.bezina.water_delivery.core.model.OrderStatus;
import com.bezina.water_delivery.order_service.DAO.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class DeliveryEventListener {
    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;

    public DeliveryEventListener(OrderRepository orderRepository, OrderStatusHistoryRepository historyRepository) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
    }

    @KafkaListener(
            topics = "delivery.events",
            groupId = "order-service",
            containerFactory = "isDeliveredKafkaListenerFactory"
    )
    public void handleDelivered(IsDeliveredEvent event) {
        System.out.println("📦 Order delivered: " + event);

        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            // обновляем статус
            order.setStatus(OrderStatus.DELIVERED);
            orderRepository.save(order);

            // пишем в историю
            OrderStatusHistory history = new OrderStatusHistory();
            history.setOrder(order);
            history.setStatus(OrderStatus.DELIVERED);
            history.setChangedAt(Instant.ofEpochMilli(event.getDeliveredAt()));
            historyRepository.save(history);
        });
    }

    @KafkaListener(
            topics = "delivery.events",
            groupId = "order-service",
            containerFactory = "deliveryStatusKafkaListenerFactory"
    )
    public void handleDeliveryStatusChanged(DeliveryStatusChangedEvent event) {
        System.out.println("📦 Delivery status update: " + event);

        orderRepository.findByOrderNo(event.getOrderNo()).ifPresent(order -> {
            order.setStatus(event.getStatus());
            orderRepository.save(order);

            OrderStatusHistory history = new OrderStatusHistory();
            history.setOrder(order);
            history.setStatus(event.getStatus());
            history.setChangedAt(Instant.ofEpochMilli(event.getChangedAt()));
            historyRepository.save(history);
        });
    }
}
