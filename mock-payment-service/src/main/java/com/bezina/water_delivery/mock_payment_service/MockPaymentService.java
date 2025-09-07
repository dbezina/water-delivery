package com.bezina.water_delivery.mock_payment_service;

import com.bezina.water_delivery.core.events.OrderCreatedEvent;
import com.bezina.water_delivery.core.events.PaymentConfirmedEvent;
import com.bezina.water_delivery.core.events.PaymentFailedEvent;
import com.bezina.water_delivery.core.events.StockReservedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class MockPaymentService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MockPaymentService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
            topics = "inventory.stock_reserved",
            groupId = "payment-service",
            containerFactory = "stockReservedKafkaListenerFactory")
    public void handleStockReserved(StockReservedEvent event) {
        // Эмулируем оплату
        boolean paymentOk = Math.random() > 0.3; // 70% успеха
     //   boolean paymentOk = false;
        if (paymentOk) {
            PaymentConfirmedEvent confirmed = new PaymentConfirmedEvent(
                    event.getOrderId(),
                    event.getOrderNo(),
                    System.currentTimeMillis()
            );
            kafkaTemplate.send("payment.confirmed", event.getOrderId(), confirmed);
            System.out.println("💳 Payment SUCCESS for order " + event.getOrderId());
        } else {
            PaymentFailedEvent failed = new PaymentFailedEvent(
                    event.getOrderId(),
                    event.getOrderNo(),
                    "Mock payment failure",
                    System.currentTimeMillis()
            );
            kafkaTemplate.send("payment.failed", event.getOrderId(), failed);
            System.out.println(" Payment FAILED for order " + event.getOrderId());
        }
    }
}
