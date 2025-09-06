package com.bezina.water_delivery.order_service.DAO;

import com.bezina.water_delivery.core.model.orders.Order;
import com.bezina.water_delivery.core.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {
    Optional<Order> findTopByUserIdOrderByCreatedAtDesc(String userId);

    Optional<Order> findTopByUserIdAndStatusOrderByCreatedAtDesc(String userId, OrderStatus status);

    Optional<Order> findByOrderNo(Long orderNo);
}
