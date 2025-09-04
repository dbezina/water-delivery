package com.bezina.water_delivery.inventory_service.rest;

import com.bezina.water_delivery.inventory_service.DAO.InventoryRepository;
import com.bezina.water_delivery.inventory_service.entity.Inventory;
import com.bezina.water_delivery.inventory_service.entity.RestockRequest;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/inventory")
public class AdminInventoryController {
    private final InventoryRepository inventoryRepository;

    public AdminInventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping("/restock")
    public ResponseEntity<List<Inventory>> restockMultiple(
            @RequestBody List<RestockRequest> requests,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String role) {

        if (!"ROLE_ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<Inventory> updatedItems = new ArrayList<>();

        for (RestockRequest itemRequest : requests) {
            if (itemRequest.getSize() == null) continue;

            Inventory item = inventoryRepository.findBySize(itemRequest.getSize())
                    .orElse(new Inventory(itemRequest.getSize(), 0));

            item.setQuantity(item.getQuantity() + itemRequest.getQuantity());
            inventoryRepository.save(item);

            updatedItems.add(item);
        }

        return ResponseEntity.ok(updatedItems);
    }
    @GetMapping
    public ResponseEntity<List<Inventory>> getAllItems(@RequestHeader("X-User-Id") String userId,
                                                       @RequestHeader("X-User-Role") String role) {
        if (!"ROLE_ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
        List<Inventory> items = inventoryRepository.findAll();
        return ResponseEntity.ok(items);
    }
}
