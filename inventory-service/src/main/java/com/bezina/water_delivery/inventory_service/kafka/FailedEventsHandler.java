package com.bezina.water_delivery.inventory_service.kafka;

import com.bezina.water_delivery.core.events.DeliveryStatusChangedEvent;
import com.bezina.water_delivery.core.events.PaymentFailedEvent;
import com.bezina.water_delivery.core.events.StockInsufficientEvent;
import com.bezina.water_delivery.core.model.enums.OrderStatus;
import com.bezina.water_delivery.inventory_service.DAO.InventoryRepository;
import com.bezina.water_delivery.inventory_service.DAO.ReservationRepository;
import com.bezina.water_delivery.inventory_service.service.ReservationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FailedEventsHandler {
    private final InventoryRepository inventoryRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public FailedEventsHandler(InventoryRepository inventoryRepository, ReservationRepository reservationRepository, ReservationService reservationService) {
        this.inventoryRepository = inventoryRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
    }

    @KafkaListener(
            topics = "payment.failed",
            groupId = "inventory-service",
            containerFactory = "paymentFailedKafkaListenerFactory")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        System.out.println("♻ Откат: платёж не прошёл, возвращаем товары " + event.getOrderId());
        reservationService.release(event.getOrderNo());
    }

    @KafkaListener(
            topics = "delivery.events.status-changed",
            groupId = "inventory-service",
            containerFactory = "deliveryStatusKafkaListenerFactory")
    public void handleDeliveryStatusChanged(DeliveryStatusChangedEvent event) {
        if (event.getStatus() == OrderStatus.CANCELLED || event.getStatus() == OrderStatus.FAILED) {
            System.out.println("♻ Откат: доставка не состоялась, возвращаем товары " + event.getOrderNo());
            reservationService.release(event.getOrderNo());
        }
    }

    @KafkaListener(
            topics = "inventory.stock_insufficient",
            groupId = "inventory-service",
            containerFactory = "stockInsufficientKafkaListenerFactory")
    public void handleStockInsufficient(StockInsufficientEvent event) {

            System.out.println("♻ Откат: нет еобходимого кол-ва на складе, возвращаем товары " + event.getOrderNo());
            reservationService.release(event.getOrderNo());

    }
}
