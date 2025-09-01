package com.bezina.water_delivery.order_service.kafka;

import com.bezina.water_delivery.core.events.StockInsufficientEvent;
import com.bezina.water_delivery.order_service.DAO.OrderRepository;
import com.bezina.water_delivery.order_service.DAO.OrderStatusHistoryRepository;
import com.bezina.water_delivery.order_service.entity.OrderStatusHistory;
import com.bezina.water_delivery.core.model.OrderStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderEventListener {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;

    public OrderEventListener(OrderRepository orderRepository,
                              OrderStatusHistoryRepository historyRepository) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
    }


    @KafkaListener(
            topics = "inventory.stock_insufficient",
            groupId = "order-service",
            containerFactory = "stockInsufficientKafkaListenerFactory"
    )
    @Transactional
    public void handleStockInsufficient(StockInsufficientEvent event) {
        //update status to CANCELLED
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            OrderStatusHistory history = new OrderStatusHistory();
            history.setOrder(order);
            history.setStatus(OrderStatus.CANCELLED);
            history.setChangedAt(Instant.now());
            historyRepository.save(history);

            System.out.printf("Order %s cancelled due to insufficient stock%n", order.getId());
        });
    }

}
