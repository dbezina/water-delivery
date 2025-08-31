package com.bezina.water_delivery.order_service.rest;

import com.bezina.water_delivery.core.DTO.OrderItemDto;
import com.bezina.water_delivery.order_service.DAO.OrderStatusHistoryRepository;
import com.bezina.water_delivery.order_service.DTO.CreateOrderRequest;

import com.bezina.water_delivery.core.events.OrderCreatedEvent;


import com.bezina.water_delivery.order_service.entity.Order;
import com.bezina.water_delivery.order_service.DAO.OrderRepository;
import com.bezina.water_delivery.order_service.kafka.OrderEventProducer;
import com.bezina.water_delivery.order_service.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;
    private final OrderEventProducer eventProducer;
    private OrderService orderService;

    public OrderController(OrderRepository orderRepository, OrderStatusHistoryRepository historyRepository, OrderEventProducer eventProducer) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
        this.eventProducer = eventProducer;
        orderService = new OrderService(orderRepository, historyRepository);

    }

   @PostMapping
   public String createOrder(@Valid @RequestBody CreateOrderRequest request) {
       // save order
       Order saved =  orderService.createOrder(request.getUserId(),request.getAddress(),request.getItems() );

       // create event
       OrderCreatedEvent event = new OrderCreatedEvent(
               saved.getId(),
               saved.getUserId(),
               saved.getItems().stream()
                       .map(i -> new OrderItemDto(i.getSize(), i.getQuantity()))
                       .collect(Collectors.toList()),
               saved.getAddress(),
               Instant.now().toEpochMilli()
       );

       // send to Kafka
       eventProducer.sendOrderCreatedEvent(event);

       return saved.getId();
   }

}
