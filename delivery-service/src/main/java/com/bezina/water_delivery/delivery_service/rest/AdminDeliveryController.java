package com.bezina.water_delivery.delivery_service.rest;

import com.bezina.water_delivery.delivery_service.DAO.AssignmentRepository;
import com.bezina.water_delivery.delivery_service.DTO.AssignRequest;
import com.bezina.water_delivery.delivery_service.DTO.UpdateStatusRequest;
import com.bezina.water_delivery.delivery_service.entity.Assignment;
import com.bezina.water_delivery.delivery_service.entity.enums.AssignmentStatus;
import com.bezina.water_delivery.delivery_service.events.DeliveryAssignedEvent;
import com.bezina.water_delivery.delivery_service.events.DeliveryStatusChangedEvent;
import com.bezina.water_delivery.delivery_service.kafka.DeliveryEventProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

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
        eventProducer.sendDeliveryEvent(new DeliveryAssignedEvent(
                saved.getOrderNo(), saved.getCourierId(),
                saved.getDeliverFrom(), saved.getDeliverTo(),
                saved.getStatus(), Instant.now().toEpochMilli()
        ));

        return saved;
    }

    // PATCH /admin/delivery/{orderId}/status {status}
    @PatchMapping("/{orderId}/status")
    public Assignment updateStatus(@RequestParam Long orderNo,
                                   @RequestBody UpdateStatusRequest request) {

       Assignment assignment = assignmentRepository.findByOrderNo(orderNo)
               .orElseThrow(() -> new RuntimeException("No delivery assignment found for order " + orderNo));

         assignment.setStatus(request.getStatus());
     //   System.out.println("status get "+request.getStatus());
    //    System.out.println("assignment "+assignment.getStatus()+ assignment.getUpdatedAt());
    //    System.out.println(assignment.toString());

        Assignment saved = assignmentRepository.save(assignment);

        // публикуем событие
        eventProducer.sendDeliveryEvent(new DeliveryStatusChangedEvent(
                saved.getOrderNo(), saved.getStatus(),
                Instant.now().toEpochMilli()
        ));
        System.out.println("DeliveryStatusChangedEvent saved"+saved.getOrderNo()+" "+ saved.getStatus());

        return saved;
    }
    @GetMapping("/assignments/all")
    public ResponseEntity<List<Assignment>> getAllAssignments(@RequestHeader("X-User-Id") String userId,
                                                              @RequestHeader("X-User-Role") String role) {
        if ("ROLE_ADMIN".equals(role)) {
            List<Assignment> assignments = assignmentRepository.findAll();
            return ResponseEntity.ok(assignments);
        }
        return (ResponseEntity<List<Assignment>>) ResponseEntity.badRequest();
    }
}
