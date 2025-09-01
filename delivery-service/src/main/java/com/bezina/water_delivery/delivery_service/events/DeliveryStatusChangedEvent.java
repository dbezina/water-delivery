package com.bezina.water_delivery.delivery_service.events;

import com.bezina.water_delivery.delivery_service.entity.enums.AssignmentStatus;

import java.util.Objects;

public class DeliveryStatusChangedEvent {
    private String orderId;
    private AssignmentStatus status;
    private long createdAt;

    public DeliveryStatusChangedEvent(String orderId, AssignmentStatus status, long createdAt) {
        this.orderId = orderId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public DeliveryStatusChangedEvent() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryStatusChangedEvent that = (DeliveryStatusChangedEvent) o;
        return createdAt == that.createdAt && Objects.equals(orderId, that.orderId) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, status, createdAt);
    }

    @Override
    public String toString() {
        return "DeliveryStatusChangedEvent{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}