package com.bezina.water_delivery.inventory_service.service;

import com.bezina.water_delivery.inventory_service.DAO.InventoryRepository;
import com.bezina.water_delivery.inventory_service.DAO.ReservationRepository;
import com.bezina.water_delivery.inventory_service.entity.Inventory;
import com.bezina.water_delivery.inventory_service.entity.Reservation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class ReservationService {

    private final InventoryRepository inventoryRepository;
    private final ReservationRepository reservationRepository;
    private final InventoryService inventoryService;

    public ReservationService(InventoryRepository inventoryRepository, ReservationRepository reservationRepository, InventoryService inventoryService) {
        this.inventoryRepository = inventoryRepository;
        this.reservationRepository = reservationRepository;
        this.inventoryService = inventoryService;
    }

    @Transactional
    public boolean reserve(Long orderNo, Map<String, Integer> items) {
        // проверяем, хватит ли товара
        for (var entry : items.entrySet()) {
            Inventory inv = inventoryRepository.findById(entry.getKey())
                    .orElseThrow(() -> new IllegalArgumentException("Нет позиции: " + entry.getKey()));
            if (inv.getQuantity() < entry.getValue()) {
                return false; // недостаточно товара
            }
        }

        // резервируем
        for (var entry : items.entrySet()) {
            Inventory inv = inventoryRepository.findById(entry.getKey()).orElseThrow();
            inv.setQuantity(inv.getQuantity() - entry.getValue());
            inventoryRepository.save(inv);
        }

        // сохраняем резервацию
        Reservation res = new Reservation();
        res.setOrderNo(orderNo);
        res.setItems(items);
        reservationRepository.save(res);

        return true;
    }

    @Transactional
    public void release(Long orderNo) {

       reservationRepository.findByOrderNo(orderNo).ifPresent(reservation -> {
           // вернуть количество обратно в Inventory
            reservation.getItems().forEach((size, quantity) -> inventoryService.increaseStock(size, quantity));
            reservationRepository.delete(reservation);
        });

    //    Reservation res = reservationRepository.findByOrderNo(orderNo)
      //          .orElseThrow(() -> new IllegalStateException("Нет резервации для orderId=" + orderNo));

      /*  for (var entry : res.getItems().entrySet()) {
            Inventory inv = inventoryRepository.findById(entry.getKey()).orElseThrow();
            inv.setQuantity(inv.getQuantity() + entry.getValue());
            inventoryRepository.save(inv);
        }*/

        //reservationRepository.delete(res);
    }
}
