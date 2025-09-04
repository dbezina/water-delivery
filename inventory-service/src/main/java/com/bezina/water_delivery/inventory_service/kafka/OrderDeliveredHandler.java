package com.bezina.water_delivery.inventory_service.kafka;

import com.bezina.water_delivery.core.events.IsDeliveredEvent;
import com.bezina.water_delivery.inventory_service.service.ReservationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.lang.module.ModuleReader;

@Service
public class OrderDeliveredHandler {
    private final ReservationService reservationService;

    public OrderDeliveredHandler(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @KafkaListener(
            topics = "delivery.events.order-delivered",
            groupId = "inventory-service",
            containerFactory = "isDeliveredKafkaListenerFactory")
    public void handleOrderDelivered(IsDeliveredEvent event) {
        System.out.println("✅ Заказ " + event.getOrderNo() + " доставлен. Чистим резервацию...");
        try {

            reservationService.release(event.getOrderNo());
        } catch (Exception e) {
            // если резервации уже нет, это не ошибка
            System.out.println(" Резервация для заказа " + event.getOrderNo() + " уже очищена");
        }
    }

}
