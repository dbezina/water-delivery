package com.bezina.water_delivery.order_service.rest;

import com.bezina.water_delivery.core.DTO.OrderItemDto;
import com.bezina.water_delivery.core.events.OrderCreatedEvent;
import com.bezina.water_delivery.core.model.Order;

import com.bezina.water_delivery.order_service.DAO.OrderRepository;
import com.bezina.water_delivery.order_service.DAO.OrderStatusHistoryRepository;
import com.bezina.water_delivery.order_service.DTO.CreateOrderRequest;
import com.bezina.water_delivery.order_service.kafka.OrderEventProducer;
import com.bezina.water_delivery.order_service.services.OrderNoGenerator;
import com.bezina.water_delivery.order_service.services.OrderService;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final OrderStatusHistoryRepository historyRepository;
    private final OrderEventProducer eventProducer;
    private final OrderNoGenerator orderNoGenerator;
    private OrderService orderService;

    public OrderController(OrderRepository orderRepository, OrderStatusHistoryRepository historyRepository, OrderEventProducer eventProducer, OrderNoGenerator orderNoGenerator) {
        this.orderRepository = orderRepository;
        this.historyRepository = historyRepository;
        this.eventProducer = eventProducer;
        this.orderNoGenerator = orderNoGenerator;
        orderService = new OrderService(orderRepository, historyRepository, this.orderNoGenerator);

    }

   @PostMapping
   public String createOrder(@Valid @RequestBody CreateOrderRequest request,
                             @RequestHeader("X-User-Id") String userId,
                             @RequestHeader("X-User-Role") String role) {
       // save order
       Order saved =  orderService.createOrder(userId,request.getAddress(),request.getItems() );

       // create event
       OrderCreatedEvent event = new OrderCreatedEvent(
               saved.getId(),
               saved.getOrderNo(),
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
