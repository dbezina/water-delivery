package com.bezina.water_delivery.delivery_service.DTO;

import com.bezina.water_delivery.core.model.OrderStatus;

public class CourierUpdateRequest {
    private OrderStatus status; // DELIVERED or FAILED

    public CourierUpdateRequest(OrderStatus status) {
        this.status = status;
    }

    public CourierUpdateRequest() {
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
