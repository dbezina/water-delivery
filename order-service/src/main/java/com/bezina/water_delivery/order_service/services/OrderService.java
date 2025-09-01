package com.bezina.water_delivery.order_service.services;

import com.bezina.water_delivery.core.DTO.OrderItemDto;
import com.bezina.water_delivery.order_service.DAO.OrderRepository;
import com.bezina.water_delivery.order_service.DAO.OrderStatusHistoryRepository;

import com.bezina.water_delivery.order_service.entity.Order;
import com.bezina.water_delivery.order_service.entity.OrderItem;
import com.bezina.water_delivery.order_service.entity.OrderStatusHistory;
import com.bezina.water_delivery.core.model.OrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;

    public OrderService(OrderRepository orderRepository,
                        OrderStatusHistoryRepository historyRepository) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
    }

    /**
     * Создание нового заказа → статус PENDING
     */

    public Order createOrder(String userId, String address, List<OrderItemDto> itemsDto) {
        Order order = new Order();
        order.setUserId(userId);
        order.setAddress(address);
        order.setStatus(OrderStatus.PENDING);

        List<OrderItem> items = itemsDto.stream()
                .map(dto -> {
                    OrderItem item = new OrderItem();
                    item.setSize(dto.getSize());
                    item.setQuantity(dto.getQuantity());
                    item.setOrder(order); // связываем с заказом
                    return item;
                })
                .toList();
        order.setItems(items);

        // сохраняем заказ
        Order savedOrder = orderRepository.save(order);

        // добавляем запись в историю
        addHistory(savedOrder, OrderStatus.PENDING);

        return savedOrder;
    }

    /**
     * Обновление статуса заказа (CONFIRMED, CANCELLED)
     */
    public Order updateStatus(String orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);

        addHistory(updatedOrder, newStatus);

        return updatedOrder;
    }

    private void addHistory(Order order, OrderStatus status) {
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setStatus(status);
        history.setChangedAt(Instant.now());

        historyRepository.save(history);
    }


}
