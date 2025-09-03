package com.bezina.water_delivery.delivery_service.events;

import com.bezina.water_delivery.delivery_service.entity.enums.AssignmentStatus;

import java.util.Objects;

public class DeliveryStatusChangedEvent {
    private Long orderNo;
    private AssignmentStatus status;
    private long createdAt;

    public DeliveryStatusChangedEvent(Long orderNo, AssignmentStatus status, long createdAt) {
        this.orderNo = orderNo;
        this.status = status;
        this.createdAt = createdAt;
    }

    public DeliveryStatusChangedEvent() {
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
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
        return createdAt == that.createdAt && Objects.equals(orderNo, that.orderNo) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, status, createdAt);
    }

    @Override
    public String toString() {
        return "DeliveryStatusChangedEvent{" +
                "orderId='" + orderNo + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}