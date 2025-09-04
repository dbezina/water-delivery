package com.bezina.water_delivery.inventory_service.DAO;

import com.bezina.water_delivery.inventory_service.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, String> {
    Optional<Reservation> findByOrderNo(Long orderNo);
}