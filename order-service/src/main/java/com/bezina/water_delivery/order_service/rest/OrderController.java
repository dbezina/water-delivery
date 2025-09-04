package com.bezina.water_delivery.order_service.rest;

import com.bezina.water_delivery.core.DTO.OrderDto;
import com.bezina.water_delivery.core.events.OrderCreatedEvent;
import com.bezina.water_delivery.core.model.Order;

import com.bezina.water_delivery.order_service.DAO.OrderRepository;
import com.bezina.water_delivery.order_service.DAO.OrderStatusHistoryRepository;
import com.bezina.water_delivery.order_service.DTO.CreateOrderRequest;
import com.bezina.water_delivery.order_service.kafka.OrderEventProducer;
import com.bezina.water_delivery.order_service.services.OrderNoGenerator;
import com.bezina.water_delivery.order_service.services.OrderService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

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
        orderService = new OrderService(orderRepository, historyRepository, this.orderNoGenerator, eventProducer);

    }

   @PostMapping
   public ResponseEntity<?> createOrder(@Valid @RequestBody CreateOrderRequest request,
                                               @RequestHeader("X-User-Id") String userId,
                                               @RequestHeader("X-User-Role") String role) {
       // save order
       try {
           // Сохраняем заказ
           Order saved = orderService.createOrder(userId, request.getAddress(), request.getItems());

           // Создаём DTO для отправки в ответ
           OrderDto orderDto = OrderDto.fromEntity(saved);

           // Создаём событие для Kafka
           OrderCreatedEvent event = new OrderCreatedEvent(
                   saved.getId(),
                   saved.getOrderNo(),
                   saved.getUserId(),
                   orderDto.getItems(),
                   saved.getAddress(),
                   Instant.now()
           );

           // Отправляем в Kafka
           eventProducer.sendOrderCreatedEvent(event);

           // Возвращаем сохранённый заказ в JSON
           return ResponseEntity.ok(orderDto);

       } catch (Exception e) {
           // Логируем ошибку
           e.printStackTrace();

           // Возвращаем сообщение об ошибке
           Map<String, String> error = Map.of(
                   "message", "Failed to create order",
                   "error", e.getMessage()
           );
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
       }
   }

}
