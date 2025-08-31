package com.bezina.water_delivery.order_service.DAO;

import com.bezina.water_delivery.order_service.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
