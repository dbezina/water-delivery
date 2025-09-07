package com.bezina.water_delivery.inventory_service.service;

import com.bezina.water_delivery.inventory_service.DAO.InventoryRepository;
import com.bezina.water_delivery.core.DTO.OrderItemDto;
import com.bezina.water_delivery.inventory_service.DAO.ReservationRepository;
import com.bezina.water_delivery.inventory_service.entity.Inventory;
import com.bezina.water_delivery.core.events.LowStockEvent;
import com.bezina.water_delivery.inventory_service.entity.Reservation;
import com.bezina.water_delivery.inventory_service.kafka.InventoryEventProducer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class InventoryService {
    private final InventoryRepository repository;
    private final ReservationRepository reservationRepository;
    private final InventoryEventProducer producer;
    private static final Logger log = LoggerFactory.getLogger("InventoryService.class");


    public InventoryService(InventoryRepository repository, ReservationRepository reservationRepository, InventoryEventProducer producer) {
        this.repository = repository;
        this.reservationRepository = reservationRepository;
        this.producer = producer;
    }
    @Transactional
    public boolean checkAndReserve(Long orderNo,List<OrderItemDto> items) {

//        // Проверка наличия
//        for (OrderItemDto item : items) {
//            Inventory inv = repository.findById(item.getSize())
//                    .orElseThrow(() -> new RuntimeException("Size not found: " + item.getSize()));
//
//            // если недостаточно, возвращаемся, чтобы сгенерировать событие sendStockInsufficient
//            if (inv.getQuantity() < item.getQuantity()) {
//                return false; // нехватка
//            }
//
//            // уменьшаем остатки
//            inv.setQuantity(inv.getQuantity() - item.getQuantity());
//            repository.save(inv);
//
//            // если после вычитания стало 0 → уведомляем админов
//            if (inv.getQuantity() == 0) {
//                producer.sendLowStockEvent(new LowStockEvent(
//                        inv.getSize(),
//                        "Product " + inv.getSize() + " is out of stock!",
//                        Instant.now()
//                       // Instant.now().toEpochMilli()
//                ));
//                System.out.println("msg for Admin  : Product " + inv.getSize() + " is out of stock!");
//            }
//        }
//        return true;
            Map<String, Integer> reservedItems = new HashMap<>();

            for (OrderItemDto item : items) {
                Inventory inv = repository.findById(item.getSize())
                        .orElseThrow(() -> new RuntimeException("Size not found: " + item.getSize()));

                if (inv.getQuantity() < item.getQuantity()) {
                    return false; // нехватка
                }

                inv.setQuantity(inv.getQuantity() - item.getQuantity());
                repository.save(inv);

                reservedItems.put(item.getSize(), item.getQuantity());

                if (inv.getQuantity() == 0) {
                    producer.sendLowStockEvent(new LowStockEvent(
                            inv.getSize(),
                            "Product " + inv.getSize() + " is out of stock!",
                            Instant.now()
                    ));
                    log.warn("⚠ Товар {} закончился на складе!", inv.getSize());
                }
            }

            // 🔹 сохраняем резервацию
            Reservation reservation = new Reservation();
            reservation.setOrderNo(orderNo);
            reservation.setItems(reservedItems);
            reservationRepository.save(reservation);

            log.info("✅ Резервация сохранена: orderNo={}, items={}", orderNo, reservedItems);

            return true;

    }

    /**
     * Увеличивает количество товара на складе по размеру.
     *
     * size     размер товара ("1L", "5L", "18L")
     * quantity количество для добавления
     */
    public void increaseStock(String size, int quantity) {

       Inventory inventory = repository.findById(size)
                    .orElseGet(() -> {
                        log.info(" Inventory для size={} не найден, создаём новый", size);
                        Inventory newInventory = new Inventory();
                        newInventory.setSize(size);
                        newInventory.setQuantity(0);
                        return newInventory;
                    });

            inventory.setQuantity(inventory.getQuantity() + quantity);
            repository.save(inventory);

            log.info("Stock обновлён: {} +{}, итог={}", size, quantity, inventory.getQuantity());


    }

    public void sendLowStockEvent (LowStockEvent event){
        producer.sendLowStockEvent(event);

    }
}
