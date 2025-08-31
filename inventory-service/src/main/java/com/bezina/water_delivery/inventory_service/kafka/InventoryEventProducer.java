package com.bezina.water_delivery.inventory_service.kafka;

import com.bezina.water_delivery.core.events.StockInsufficientEvent;
import com.bezina.water_delivery.inventory_service.events.LowStockEvent;
import com.bezina.water_delivery.inventory_service.events.StockReservedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventProducer {

        private final KafkaTemplate<String, Object> kafkaTemplate;

        public InventoryEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
            this.kafkaTemplate = kafkaTemplate;
        }

        public void sendStockReserved(StockReservedEvent event) {
            kafkaTemplate.send("inventory.stock_reserved", event.getOrderId(), event);
        }

        public void sendStockInsufficient(StockInsufficientEvent event) {
            kafkaTemplate.send("inventory.stock_insufficient", event.getOrderId(), event);
            //System.out.println("event to topic inventory.stock_insufficient is send "+event);
        }

        public void sendLowStockEvent(LowStockEvent event) {
            kafkaTemplate.send("admin.low_stock", event.getSize(), event);
        }


}
