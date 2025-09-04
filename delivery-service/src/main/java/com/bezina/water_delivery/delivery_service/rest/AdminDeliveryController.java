package com.bezina.water_delivery.delivery_service.rest;

import com.bezina.water_delivery.delivery_service.DAO.AssignmentRepository;
import com.bezina.water_delivery.delivery_service.DTO.AssignRequest;
import com.bezina.water_delivery.core.DTO.AssignmentDto;
import com.bezina.water_delivery.delivery_service.DTO.UpdateStatusRequest;
import com.bezina.water_delivery.core.model.Assignment;
import com.bezina.water_delivery.core.model.enums.AssignmentStatus;
import com.bezina.water_delivery.delivery_service.events.DeliveryAssignedEvent;
import com.bezina.water_delivery.delivery_service.events.DeliveryStatusChangedEvent;
import com.bezina.water_delivery.delivery_service.kafka.DeliveryEventProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/delivery")
public class AdminDeliveryController {

    private final AssignmentRepository assignmentRepository;
    private final DeliveryEventProducer eventProducer;

    public AdminDeliveryController(AssignmentRepository assignmentRepository,
                                   DeliveryEventProducer eventProducer) {
        this.assignmentRepository = assignmentRepository;
        this.eventProducer = eventProducer;
    }

    // POST /admin/delivery/assign {orderId, courierId?}
    @PostMapping("/assign")
    public Assignment assignDelivery(@RequestBody AssignRequest request,
                                     @RequestHeader("X-User-Id") String userId,
                                     @RequestHeader("X-User-Role") String role
    ) {
        Assignment assignment = new Assignment();
        assignment.setOrderNo(request.getOrderNo());
        assignment.setCourierId(request.getCourierId());
        assignment.setStatus(AssignmentStatus.QUEUED);
        assignment.setDeliverFrom(Instant.now().plus(3, ChronoUnit.HOURS));
        assignment.setDeliverTo(Instant.now().plus(4, ChronoUnit.HOURS));

        System.out.println(assignment.toString());

        Assignment saved = assignmentRepository.save(assignment);

        // публикуем событие
        eventProducer.sendDeliveryAssignedEvent(new DeliveryAssignedEvent(
                saved.getOrderNo(), saved.getCourierId(),
                saved.getDeliverFrom(), saved.getDeliverTo(),
                saved.getStatus(), Instant.now().toEpochMilli()
        ));

        return saved;
    }

    // PATCH /admin/delivery/{orderId}/status {status}
    @PatchMapping("/{orderNo}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long orderNo,
                                          @RequestBody UpdateStatusRequest request) {
        try {
            // Ищем назначение доставки по номеру заказа
            Assignment assignment = assignmentRepository.findByOrderNo(orderNo)
                    .orElseThrow(() -> new RuntimeException(
                            "No delivery assignment found for order " + orderNo));

            // Обновляем статус
            assignment.setStatus(request.getStatus());
            assignment.setUpdatedAt(Instant.now());

            Assignment saved = assignmentRepository.save(assignment);

            // Публикуем событие в Kafka
            DeliveryStatusChangedEvent event = new DeliveryStatusChangedEvent(
                    saved.getOrderNo(),
                    saved.getStatus(),
                    Instant.now().toEpochMilli()
            );
            eventProducer.sendDeliveryStatusChanged(event);

            System.out.println("✅ DeliveryStatusChangedEvent published: orderNo="
                    + saved.getOrderNo() + ", status=" + saved.getStatus());

            // Возвращаем JSON с обновленным статусом
            Map<String, Object> response = Map.of(
                    "orderNo", saved.getOrderNo(),
                    "status", saved.getStatus()
            );
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = Map.of(
                    "message", "Failed to update delivery status",
                    "error", e.getMessage()
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    @GetMapping("/assignments/all")
    public ResponseEntity<List<AssignmentDto>> getAllAssignments(@RequestHeader("X-User-Id") String userId,
                                                                 @RequestHeader("X-User-Role") String role) {
        if ("ROLE_ADMIN".equals(role)) {
            List<AssignmentDto> dtos = assignmentRepository.findAll()
                    .stream()
                    .map(AssignmentDto::fromEntity)
                    .toList();
            return ResponseEntity.ok(dtos);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

}
