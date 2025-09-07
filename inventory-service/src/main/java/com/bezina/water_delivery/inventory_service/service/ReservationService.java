package com.bezina.water_delivery.inventory_service.service;

import com.bezina.water_delivery.inventory_service.DAO.InventoryRepository;
import com.bezina.water_delivery.inventory_service.DAO.ReservationRepository;
import com.bezina.water_delivery.inventory_service.entity.Inventory;
import com.bezina.water_delivery.inventory_service.entity.Reservation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ReservationService {

    private final InventoryRepository inventoryRepository;
    private final ReservationRepository reservationRepository;
    private final InventoryService inventoryService;
    private static final Logger log = LoggerFactory.getLogger("ReservationService.class");

    public ReservationService(InventoryRepository inventoryRepository, ReservationRepository reservationRepository, InventoryService inventoryService) {
        this.inventoryRepository = inventoryRepository;
        this.reservationRepository = reservationRepository;
        this.inventoryService = inventoryService;
    }



    @Transactional
    public void release(Long orderNo) {

            reservationRepository.findByOrderNo(orderNo).ifPresentOrElse(reservation -> {
                reservation.getItems().forEach((size, quantity) -> {
                    inventoryService.increaseStock(size, quantity);
                });
                reservationRepository.delete(reservation);
                log.info("🗑 Резервация удалена");
            }, () -> {
                log.warn("❌ Резервация не найдена для orderNo="+ orderNo);
            });
    }
}
