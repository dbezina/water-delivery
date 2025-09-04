package com.bezina.water_delivery.delivery_service.service;

import com.bezina.water_delivery.core.events.OrderConfirmedEvent;
import com.bezina.water_delivery.delivery_service.DAO.AssignmentDetailsRepository;
import com.bezina.water_delivery.delivery_service.DAO.AssignmentRepository;
import com.bezina.water_delivery.core.model.Assignment;
import com.bezina.water_delivery.core.model.AssignmentDetails;
import com.bezina.water_delivery.core.model.AssignmentItem;
import com.bezina.water_delivery.core.model.enums.AssignmentStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentDetailsRepository assignmentDetailsRepository;

    public AssignmentService(AssignmentRepository assignmentRepository, AssignmentDetailsRepository assignmentDetailsRepository) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentDetailsRepository = assignmentDetailsRepository;
    }
    @Transactional
    public Assignment createAssignmentToConfirmedEvent (OrderConfirmedEvent event){
        Assignment assignment = new Assignment();
        assignment.setOrderNo(event.getOrderNo());
        assignment.setStatus(AssignmentStatus.QUEUED);
        assignment.setCourierId("cour-5");
        assignment.setDeliverFrom(Instant.now().plus(3, ChronoUnit.HOURS));
        assignment.setDeliverTo(Instant.now().plus(4, ChronoUnit.HOURS));

        AssignmentDetails details = new AssignmentDetails();
        details.setAssignment(assignment);
        details.setUserId(event.getUserId());
        details.setAddress(event.getAddress());

        List<AssignmentItem> items = event.getItems().stream()
                .map(dto -> {
                    AssignmentItem item = new AssignmentItem();
                    item.setAssignmentDetails(details);
                    item.setSize(dto.getSize());
                    item.setQuantity(dto.getQuantity());
                    return item;
                })
                .toList();

        details.setItems(items);
        assignment.setDetails(details);

        return assignmentRepository.save(assignment);
    }

}
