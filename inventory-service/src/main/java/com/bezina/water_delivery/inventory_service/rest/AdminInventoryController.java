package com.bezina.water_delivery.inventory_service.rest;

import com.bezina.water_delivery.inventory_service.DAO.InventoryRepository;
import com.bezina.water_delivery.inventory_service.entity.Inventory;
import com.bezina.water_delivery.inventory_service.entity.RestockRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/inventory")
public class AdminInventoryController {
    private final InventoryRepository inventoryRepository;

    public AdminInventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @PostMapping("/restock")
    public ResponseEntity<String> restock(@RequestBody RestockRequest request,
                                          @RequestHeader("X-User-Id") String userId,
                                          @RequestHeader("X-User-Role") String role) {

        if (!"ROLE_ADMIN".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }

        Inventory item = inventoryRepository.findById(request.getSize())
                .orElse(new Inventory(request.getSize(), 0));

        item.setQuantity(item.getQuantity() + request.getQuantity());
        inventoryRepository.save(item);

        return ResponseEntity.ok("Restocked " + request.getSize() + " with " + request.getQuantity());
    }
}
