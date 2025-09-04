package com.bezina.water_delivery.inventory_service.kafka;


import com.bezina.water_delivery.core.events.OrderCreatedEvent;
import com.bezina.water_delivery.core.events.StockInsufficientEvent;
import com.bezina.water_delivery.inventory_service.DAO.InventoryRepository;
import com.bezina.water_delivery.inventory_service.events.StockReservedEvent;
import com.bezina.water_delivery.inventory_service.service.InventoryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventHandler {

    private final InventoryRepository inventoryRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC_RESERVED = "inventory.stock_reserved";
    private static final String TOPIC_INSUFFICIENT = "inventory.stock_insufficient";
    private final InventoryService inventoryService;
    private final InventoryEventProducer producer;
    public InventoryEventHandler(InventoryRepository inventoryRepository, KafkaTemplate<String, Object> kafkaTemplate, InventoryService inventoryService, InventoryEventProducer producer) {
        this.inventoryRepository = inventoryRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.inventoryService = inventoryService;
        this.producer = producer;
    }


    @KafkaListener(
            topics = "orders.order_created",
            groupId = "inventory-service",
            containerFactory = "orderCreatedKafkaListenerFactory"
    )
    public void handleOrderCreated(OrderCreatedEvent event) {

        System.out.println("📦 Получено событие из Kafka: " + event);
        boolean reserved = inventoryService.checkAndReserve(event.getItems());

        if (reserved) {
            System.out.println("✅ Склад зарезервировал товары для заказа " + event.getOrderId());
            producer.sendStockReserved(new StockReservedEvent(
                    event.getOrderId(),
                    event.getUserId(),
                    event.getItems(),
                    event.getAddress(),
                    event.getCreatedAt()
            ));
        } else {
            System.out.println("❌ Недостаточно товара для заказа " + event.getOrderId());
            producer.sendStockInsufficient(new StockInsufficientEvent(
                    event.getOrderId(),
                    event.getOrderNo(),
                    "Not enough stock available",
                    event.getCreatedAt()
            ));
        }

    }
}
