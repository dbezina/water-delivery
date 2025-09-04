package com.bezina.water_delivery.delivery_service.DTO;

import com.bezina.water_delivery.core.model.enums.AssignmentStatus;

public class CourierUpdateRequest {
    private AssignmentStatus status; // DELIVERED or FAILED

    public CourierUpdateRequest(AssignmentStatus status) {
        this.status = status;
    }

    public CourierUpdateRequest() {
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }
}
