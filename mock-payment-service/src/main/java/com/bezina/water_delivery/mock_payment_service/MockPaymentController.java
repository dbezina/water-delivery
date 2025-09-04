package com.bezina.water_delivery.mock_payment_service;

import com.bezina.water_delivery.core.events.PaymentConfirmedEvent;
import com.bezina.water_delivery.core.events.PaymentFailedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mock-payments")
public class MockPaymentController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MockPaymentController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/{orderId}/confirm")
    public String confirm(@PathVariable String orderId) {
        PaymentConfirmedEvent event = new PaymentConfirmedEvent(orderId,Long.getLong(orderId), System.currentTimeMillis());
        kafkaTemplate.send("payment.confirmed", orderId, event);
        return "Payment CONFIRMED for order " + orderId;
    }

    @PostMapping("/{orderId}/fail")
    public String fail(@PathVariable String orderId) {
        PaymentFailedEvent event = new PaymentFailedEvent(orderId, Long.getLong(orderId), "Manual fail", System.currentTimeMillis());
        kafkaTemplate.send("payment.failed", orderId, event);
        return "Payment FAILED for order " + orderId;
    }
}
