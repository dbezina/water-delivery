package com.bezina.water_delivery.inventory_service.DAO;

import com.bezina.water_delivery.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
}
