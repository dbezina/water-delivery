package com.bezina.water_delivery.order_service.kafka;

import com.bezina.water_delivery.core.events.PaymentConfirmedEvent;
import com.bezina.water_delivery.core.events.PaymentFailedEvent;
import com.bezina.water_delivery.core.model.OrderStatus;
import com.bezina.water_delivery.core.model.OrderStatusHistory;
import com.bezina.water_delivery.order_service.DAO.OrderRepository;
import com.bezina.water_delivery.order_service.DAO.OrderStatusHistoryRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PaymentEventListener {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;

    public PaymentEventListener(OrderRepository orderRepository,
                                OrderStatusHistoryRepository historyRepository) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
    }

    @KafkaListener(topics = "payment.confirmed", groupId = "order-service",
            containerFactory = "paymentConfirmedKafkaListenerFactory")
    public void handleConfirmed(PaymentConfirmedEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            if (order.getStatus() == OrderStatus.PENDING) {
                order.setStatus(OrderStatus.CONFIRMED);
                orderRepository.save(order);

                // пишем в историю
                OrderStatusHistory history = new OrderStatusHistory();
                history.setOrder(order);
                history.setStatus(OrderStatus.CONFIRMED);
                history.setChangedAt(Instant.ofEpochMilli(event.getConfirmedAt()));
                historyRepository.save(history);

                 System.out.println("✅ Order " + order.getId() + " confirmed");
            }
        });
    }

    @KafkaListener(topics = "payment.failed", groupId = "order-service",
            containerFactory = "paymentFailedKafkaListenerFactory")
    public void handleFailed(PaymentFailedEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            if (order.getStatus() == OrderStatus.PENDING) {
                order.setStatus(OrderStatus.CANCELLED);
                orderRepository.save(order);

                // пишем в историю
                OrderStatusHistory history = new OrderStatusHistory();
                history.setOrder(order);
                history.setStatus(OrderStatus.CANCELLED);
                history.setChangedAt(Instant.ofEpochMilli(event.getFailedAt()));
                historyRepository.save(history);

                System.out.println("❌ Order " + order.getId() + " cancelled (payment failed)");
            }
        });
    }
}
