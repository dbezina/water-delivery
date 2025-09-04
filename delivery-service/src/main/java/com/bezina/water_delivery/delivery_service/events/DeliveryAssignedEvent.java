package com.bezina.water_delivery.delivery_service.events;

import com.bezina.water_delivery.core.model.enums.AssignmentStatus;

import java.time.Instant;
import java.util.Objects;

public class DeliveryAssignedEvent {
    private Long orderNo;
    private String courierId;
    private Instant deliverFrom;
    private Instant deliverTo;
    private AssignmentStatus status;
    private long createdAt;

    public DeliveryAssignedEvent(Long orderNo, String courierId, Instant deliverFrom, Instant deliverTo, AssignmentStatus status, long createdAt) {
        this.orderNo = orderNo;
        this.courierId = courierId;
        this.deliverFrom = deliverFrom;
        this.deliverTo = deliverTo;
        this.status = status;
        this.createdAt = createdAt;
    }

    public DeliveryAssignedEvent() {
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public Instant getDeliverFrom() {
        return deliverFrom;
    }

    public void setDeliverFrom(Instant deliverFrom) {
        this.deliverFrom = deliverFrom;
    }

    public Instant getDeliverTo() {
        return deliverTo;
    }

    public void setDeliverTo(Instant deliverTo) {
        this.deliverTo = deliverTo;
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
        DeliveryAssignedEvent that = (DeliveryAssignedEvent) o;
        return createdAt == that.createdAt && Objects.equals(orderNo, that.orderNo) && Objects.equals(courierId, that.courierId) && Objects.equals(deliverFrom, that.deliverFrom) && Objects.equals(deliverTo, that.deliverTo) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, courierId, deliverFrom, deliverTo, status, createdAt);
    }

    @Override
    public String toString() {
        return "DeliveryAssignedEvent{" +
                "orderNo=" + orderNo +
                ", courierId='" + courierId + '\'' +
                ", deliverFrom=" + deliverFrom +
                ", deliverTo=" + deliverTo +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
