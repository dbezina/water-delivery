package com.bezina.water_delivery.order_service.rest;


import com.bezina.water_delivery.core.DTO.OrderDto;
import com.bezina.water_delivery.core.model.enums.OrderStatus;
import com.bezina.water_delivery.order_service.DAO.OrderRepository;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/orders")
public class UserOrderController {

    private final OrderRepository orderRepository;

    public UserOrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

  @GetMapping("/current")
  public ResponseEntity<OrderDto> getCurrentOrder(@RequestHeader("X-User-Id") String userId,
                                                  @RequestHeader("X-User-Role") String role) {
      // Теперь userId мы получаем из заголовка, который пробросил Gateway
      return orderRepository.findTopByUserIdOrderByCreatedAtDesc(userId)
              .map(OrderDto::fromEntity)
              .map(ResponseEntity::ok)
              .orElse(ResponseEntity.notFound().build());
  }
    @GetMapping("/last-success")
    public ResponseEntity<OrderDto> getLastSuccessfulOrder(@RequestHeader("X-User-Id") String userId,
                                                           @RequestHeader("X-User-Role") String role) {

        return orderRepository.findTopByUserIdAndStatusOrderByCreatedAtDesc(
                        userId,
                       // OrderStatus.DELIVERED
                OrderStatus.CANCELLED
                )
                .map(OrderDto::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
