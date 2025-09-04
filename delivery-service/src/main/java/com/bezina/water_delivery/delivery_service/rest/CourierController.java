package com.bezina.water_delivery.delivery_service.rest;


import com.bezina.water_delivery.delivery_service.DAO.AssignmentRepository;
import com.bezina.water_delivery.core.DTO.AssignmentDto;
import com.bezina.water_delivery.delivery_service.DTO.CourierUpdateRequest;
import com.bezina.water_delivery.core.model.Assignment;
import com.bezina.water_delivery.delivery_service.service.UpdateMessages;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/courier")
public class CourierController {

    private final AssignmentRepository assignmentRepository;
    private final UpdateMessages updateService;

    public CourierController(AssignmentRepository assignmentRepository, UpdateMessages updateService) {
        this.assignmentRepository = assignmentRepository;
        this.updateService = updateService;
    }

    @PatchMapping("/{orderNo}/status")
    public ResponseEntity<Map<String, Object>> updateOrderStatus(
            @PathVariable("orderNo") Long orderNo,
            @RequestBody CourierUpdateRequest request,
            @RequestHeader("X-User-Id") String userId,
            @RequestHeader("X-User-Role") String role) {

        if (!"ROLE_COURIER".equals(role)) {
            Map<String, Object> response = new HashMap<>();
            response.put("orderNo", orderNo);
            response.put("status", ResponseEntity.status(HttpStatus.FORBIDDEN));
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
        Assignment assignment = assignmentRepository.findByOrderNo(orderNo)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not found for orderId " + orderNo));

        assignment.setStatus(request.getStatus());
        assignment.setUpdatedAt(Instant.now());

        assignmentRepository.save(assignment);
        updateService.sendIsDeliveredMessageToKafka(assignment);
       // return ResponseEntity.ok("Order " + orderNo + " updated to " + request.getStatus());
        Map<String, Object> response = new HashMap<>();
        response.put("orderNo", orderNo);
        response.put("status", request.getStatus());

        return ResponseEntity.ok(response);
    }
    @GetMapping("/assignments/my")
    public ResponseEntity<List<AssignmentDto>> getAllAssignments(@RequestHeader("X-User-Id") String userId,
                                                                @RequestHeader("X-User-Role") String role) {
        if ("ROLE_COURIER".equals(role)) {
            List<AssignmentDto> dtos = assignmentRepository.findAllByCourierId(userId)
                    .stream()
                    .map(AssignmentDto::fromEntity)
                    .toList();
            return ResponseEntity.ok(dtos);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
