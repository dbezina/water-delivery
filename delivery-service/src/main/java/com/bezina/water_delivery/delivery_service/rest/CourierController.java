package com.bezina.water_delivery.delivery_service.rest;

import com.bezina.water_delivery.core.service.OrderService;
import com.bezina.water_delivery.delivery_service.DTO.CourierUpdateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courier/orders")
@PreAuthorize("hasRole('COURIER')")
public class CourierController {

    private final OrderService orderService;

    public CourierController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable String orderId,
            @RequestBody CourierUpdateRequest request) {

        orderService.updateStatusFromCourier(orderId, request.getStatus());
        return ResponseEntity.ok("Order " + orderId + " updated to " + request.getStatus());
    }
}
