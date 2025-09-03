package com.bezina.water_delivery.delivery_service.rest;


import com.bezina.water_delivery.delivery_service.DAO.AssignmentRepository;
import com.bezina.water_delivery.delivery_service.DTO.CourierUpdateRequest;
import com.bezina.water_delivery.delivery_service.entity.Assignment;
import com.bezina.water_delivery.delivery_service.service.UpdateMessages;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/courier")
public class CourierController {

    private final AssignmentRepository assignmentRepository;
    private final UpdateMessages updateService;

    public CourierController(AssignmentRepository assignmentRepository, UpdateMessages updateService) {
        this.assignmentRepository = assignmentRepository;
        this.updateService = updateService;
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> updateOrderStatus(
            @RequestParam Long orderNo,
            @RequestBody CourierUpdateRequest request,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String role) {

        if (!"ROLE_COURIER".equals(role)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
        }
        Assignment assignment = assignmentRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found for orderId " + orderNo));

        assignment.setStatus(request.getStatus());
        assignment.setUpdatedAt(Instant.now());

        assignmentRepository.save(assignment);
        updateService.sendUpdateStatusMessageToKafka(assignment);
        return ResponseEntity.ok("Order " + orderNo + " updated to " + request.getStatus());
    }
    @GetMapping("/assignments/my")
    public ResponseEntity<List<Assignment>> getAllAssignments(@RequestHeader("X-User-Id") String userId,
                                                              @RequestHeader("X-User-Role") String role) {
        if ("ROLE_COURIER".equals(role)) {
            List<Assignment> assignments = assignmentRepository.findAllByCourierId(userId);
            return ResponseEntity.ok(assignments);
        }
        return (ResponseEntity<List<Assignment>>) ResponseEntity.badRequest();
    }
}
