package com.bezina.water_delivery.delivery_service.DTO;

import com.bezina.water_delivery.core.model.enums.OrderStatus;

import java.util.Objects;

public class UpdateStatusRequest {
    private OrderStatus status;

    public UpdateStatusRequest(OrderStatus status) {
        this.status = status;
    }

    public UpdateStatusRequest() {
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
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
