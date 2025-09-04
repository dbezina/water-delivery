package com.bezina.water_delivery.inventory_service.DAO;

import com.bezina.water_delivery.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
    List<Inventory> findAll();

    Optional<Inventory> findBySize(String size);
}
