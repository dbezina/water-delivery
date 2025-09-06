package com.bezina.water_delivery.order_service.services;

import com.bezina.water_delivery.core.DTO.OrderItemDto;
import com.bezina.water_delivery.core.events.OrderConfirmedEvent;
import com.bezina.water_delivery.core.events.PaymentConfirmedEvent;
import com.bezina.water_delivery.core.model.orders.OrderItem;
import com.bezina.water_delivery.core.model.orders.OrderStatusHistory;


import com.bezina.water_delivery.core.model.orders.Order;
import com.bezina.water_delivery.core.model.enums.OrderStatus;
import com.bezina.water_delivery.order_service.DAO.OrderRepository;
import com.bezina.water_delivery.order_service.DAO.OrderStatusHistoryRepository;
import com.bezina.water_delivery.order_service.kafka.OrderEventProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;
    private final OrderNoGenerator orderNoGenerator;
    private final OrderEventProducer eventProducer;

    public OrderService(OrderRepository orderRepository,
                        OrderStatusHistoryRepository historyRepository, OrderNoGenerator orderNoGenerator, OrderEventProducer eventProducer) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
        this.orderNoGenerator = orderNoGenerator;
        this.eventProducer = eventProducer;
    }

    /**
     * Создание нового заказа → статус PENDING
     */

    public Order createOrder(String userId, String address, List<OrderItemDto> itemsDto) {
        Order order = new Order();

        if (order.getOrderNo() == null) {
            order.setOrderNo(orderNoGenerator.nextOrderNo());
        }
        order.setUserId(userId);
        order.setAddress(address);
        order.setStatus(OrderStatus.QUEUED);

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
        addHistory(savedOrder, OrderStatus.QUEUED);

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
    public void updateStatusFromDelivery(long orderNo, OrderStatus orderStatus) {

        orderRepository.findByOrderNo(orderNo).ifPresent(order -> {

            order.setStatus(orderStatus);
            orderRepository.save(order);

            // пишем в историю
            OrderStatusHistory history = new OrderStatusHistory();
            history.setOrder(order);
            history.setStatus(orderStatus);
            history.setChangedAt(Instant.now());
            historyRepository.save(history);

            System.out.println("✅ Order no" +orderNo+ " " + order.getId() + " is "+ orderStatus.name());

        });
    }

    public void confirmOrder(PaymentConfirmedEvent event) {
        orderRepository.findById(event.getOrderId()).ifPresent(order -> {
            if (order.getStatus() == OrderStatus.QUEUED) {
                order.setStatus(OrderStatus.CONFIRMED);
                orderRepository.save(order);

                // пишем в историю
                OrderStatusHistory history = new OrderStatusHistory();
                history.setOrder(order);
                history.setStatus(OrderStatus.CONFIRMED);
                history.setChangedAt(Instant.now());
                historyRepository.save(history);

                // генерируем событие order.confirmed
                OrderConfirmedEvent confirmedEvent = OrderConfirmedEvent.fromOrder(order);
                eventProducer.sendOrderConfirmedEvent(confirmedEvent);

                System.out.println("✅ Order " + order.getId() + " confirmed and published");
            }
        });
    }
}
