package com.bezina.water_delivery.order_service.DAO;

import com.bezina.water_delivery.order_service.entity.OrderStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {};
