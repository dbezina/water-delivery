package com.bezina.water_delivery.delivery_service.kafka;

import com.bezina.water_delivery.core.DTO.AssignmentItemDto;
import com.bezina.water_delivery.core.events.CourierAssignmentEvent;
import com.bezina.water_delivery.core.events.OrderConfirmedEvent;
import com.bezina.water_delivery.core.model.Assignment;
import com.bezina.water_delivery.delivery_service.service.AssignmentService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConfirmedOrderListener {
    private final AssignmentService assignmentService;
    private final DeliveryEventProducer eventProducer;

    public ConfirmedOrderListener(AssignmentService assignmentService, DeliveryEventProducer eventProducer) {
        this.assignmentService = assignmentService;
        this.eventProducer = eventProducer;
    }

    @KafkaListener(
                topics = "orders.order_confirmed",
                groupId = "admin-service",
                containerFactory = "orderConfirmedKafkaListenerFactory"
        )
    public void handleConfirmedOrder(OrderConfirmedEvent event) {

            System.out.println("✅ Admin received confirmed order: " + event.getOrderId());
            Assignment newAssignment = assignmentService.createAssignmentToConfirmedEvent(event);
            if (newAssignment != null) {
                // опубликовать событие в Kafka
                CourierAssignmentEvent courierEvent = new CourierAssignmentEvent(
                        newAssignment.getId(),
                        newAssignment.getOrderNo(),
                        newAssignment.getCourierId(),
                        newAssignment.getDetails().getUserId(),
                        newAssignment.getDetails().getItems().stream()
                                .map(i -> new AssignmentItemDto(i.getSize(), i.getQuantity()))
                                .toList(),
                        newAssignment.getDetails().getAddress(),
                        newAssignment.getDeliverFrom(),
                        newAssignment.getDeliverTo()
                );


                eventProducer.sendCourierAssignmentEvent(courierEvent);
            }
    }
}
