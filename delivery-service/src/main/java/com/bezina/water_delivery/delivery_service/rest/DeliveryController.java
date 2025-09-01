package com.bezina.water_delivery.delivery_service.rest;

import com.bezina.water_delivery.delivery_service.DAO.AssignmentRepository;
import com.bezina.water_delivery.delivery_service.DTO.AssignRequest;
import com.bezina.water_delivery.delivery_service.DTO.UpdateStatusRequest;
import com.bezina.water_delivery.delivery_service.entity.Assignment;
import com.bezina.water_delivery.delivery_service.entity.enums.AssignmentStatus;
import com.bezina.water_delivery.delivery_service.events.DeliveryAssignedEvent;
import com.bezina.water_delivery.delivery_service.events.DeliveryStatusChangedEvent;
import com.bezina.water_delivery.delivery_service.kafka.DeliveryEventProducer;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping("/admin/delivery")
public class DeliveryController {

    private final AssignmentRepository assignmentRepository;
    private final DeliveryEventProducer eventProducer;

    public DeliveryController(AssignmentRepository assignmentRepository,
                              DeliveryEventProducer eventProducer) {
        this.assignmentRepository = assignmentRepository;
        this.eventProducer = eventProducer;
    }

    // POST /admin/delivery/assign {orderId, courierId?}
    @PostMapping("/assign")
    public Assignment assignDelivery(@RequestBody AssignRequest request) {
        Assignment assignment = new Assignment();
        assignment.setOrderId(request.getOrderId());
        assignment.setCourierId(request.getCourierId());
        assignment.setStatus(AssignmentStatus.QUEUED);
        assignment.setEtaFrom(Instant.now().plus(1, ChronoUnit.HOURS));
        assignment.setEtaTo(Instant.now().plus(2, ChronoUnit.HOURS));

        Assignment saved = assignmentRepository.save(assignment);

        // публикуем событие
        eventProducer.sendDeliveryEvent(new DeliveryAssignedEvent(
                saved.getOrderId(), saved.getCourierId(),
                saved.getEtaFrom(), saved.getEtaTo(),
                saved.getStatus(), Instant.now().toEpochMilli()
        ));

        return saved;
    }

    // PATCH /admin/delivery/{orderId}/status {status}
    @PatchMapping("/{orderId}/status")
    public Assignment updateStatus(@PathVariable String orderId,
                                   @RequestBody UpdateStatusRequest request) {
        Assignment assignment = assignmentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("No delivery assignment found for order " + orderId));

        assignment.setStatus(request.getStatus());
        Assignment saved = assignmentRepository.save(assignment);

        // публикуем событие
        eventProducer.sendDeliveryEvent(new DeliveryStatusChangedEvent(
                saved.getOrderId(), saved.getStatus(),
                Instant.now().toEpochMilli()
        ));
        System.out.println("DeliveryStatusChangedEvent saved"+saved.getOrderId()+" "+ saved.getStatus());

        return saved;
    }
}
