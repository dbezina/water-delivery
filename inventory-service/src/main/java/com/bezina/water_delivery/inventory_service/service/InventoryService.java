package com.bezina.water_delivery.inventory_service.service;

import com.bezina.water_delivery.inventory_service.DAO.InventoryRepository;
import com.bezina.water_delivery.core.DTO.OrderItemDto;
import com.bezina.water_delivery.inventory_service.entity.Inventory;
import com.bezina.water_delivery.core.events.LowStockEvent;
import com.bezina.water_delivery.inventory_service.kafka.InventoryEventProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository repository;
    private final InventoryEventProducer producer;

    public InventoryService(InventoryRepository repository, InventoryEventProducer producer) {
        this.repository = repository;
        this.producer = producer;
    }
    @Transactional
    public boolean checkAndReserve(List<OrderItemDto> items) {

        // Проверка наличия
        for (OrderItemDto item : items) {
            Inventory inv = repository.findById(item.getSize())
                    .orElseThrow(() -> new RuntimeException("Size not found: " + item.getSize()));

            // если недостаточно, возвращаемся, чтобы сгенерировать событие sendStockInsufficient
            if (inv.getQuantity() < item.getQuantity()) {
                return false; // нехватка
            }

            // уменьшаем остатки
            inv.setQuantity(inv.getQuantity() - item.getQuantity());
            repository.save(inv);

            // если после вычитания стало 0 → уведомляем админов
            if (inv.getQuantity() == 0) {
                producer.sendLowStockEvent(new LowStockEvent(
                        inv.getSize(),
                        "Product " + inv.getSize() + " is out of stock!",
                        Instant.now()
                       // Instant.now().toEpochMilli()
                ));
                System.out.println("msg for Admin  : Product " + inv.getSize() + " is out of stock!");
            }
        }
        return true;
    }

    /**
     * Увеличивает количество товара на складе по размеру.
     *
     * @param size     размер товара ("1L", "5L", "18L")
     * @param quantity количество для добавления
     */
    public void increaseStock(String size, int quantity) {
        Inventory inventory = repository.findById(size)
                .orElseGet(() -> {
                    // если записи нет — создаем новую
                    Inventory newInventory = new Inventory();
                    newInventory.setSize(size);
                    newInventory.setQuantity(0);
                    return newInventory;
                });

        inventory.setQuantity(inventory.getQuantity() + quantity);
        repository.save(inventory);

        System.out.printf("✅ Stock increased: %s +%d, new quantity=%d%n",
                size, quantity, inventory.getQuantity());
    }
}
