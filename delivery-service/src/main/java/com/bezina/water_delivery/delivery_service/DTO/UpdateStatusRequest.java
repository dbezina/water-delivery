package com.bezina.water_delivery.delivery_service.DTO;

import com.bezina.water_delivery.delivery_service.entity.enums.AssignmentStatus;

import java.util.Objects;

public class UpdateStatusRequest {
    private AssignmentStatus status;

    public UpdateStatusRequest(AssignmentStatus status) {
        this.status = status;
    }

    public UpdateStatusRequest() {
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateStatusRequest that = (UpdateStatusRequest) o;
        return status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(status);
    }

    @Override
    public String toString() {
        return "UpdateStatusRequest{" +
                "status=" + status +
                '}';
    }
}
