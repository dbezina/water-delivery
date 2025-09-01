package com.bezina.water_delivery.delivery_service.events;

import com.bezina.water_delivery.delivery_service.entity.enums.AssignmentStatus;

import java.time.Instant;
import java.util.Objects;

public class DeliveryAssignedEvent {
    private String orderId;
    private String courierId;
    private Instant etaFrom;
    private Instant etaTo;
    private AssignmentStatus status;
    private long createdAt;

    public DeliveryAssignedEvent(String orderId,
                                 String courierId,
                                 Instant etaFrom,
                                 Instant etaTo,
                                 AssignmentStatus status,
                                 long createdAt) {
        this.orderId = orderId;
        this.courierId = courierId;
        this.etaFrom = etaFrom;
        this.etaTo = etaTo;
        this.status = status;
        this.createdAt = createdAt;
    }

    public DeliveryAssignedEvent() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public Instant getEtaFrom() {
        return etaFrom;
    }

    public void setEtaFrom(Instant etaFrom) {
        this.etaFrom = etaFrom;
    }

    public Instant getEtaTo() {
        return etaTo;
    }

    public void setEtaTo(Instant etaTo) {
        this.etaTo = etaTo;
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
        return createdAt == that.createdAt && Objects.equals(orderId, that.orderId) && Objects.equals(courierId, that.courierId) && Objects.equals(etaFrom, that.etaFrom) && Objects.equals(etaTo, that.etaTo) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, courierId, etaFrom, etaTo, status, createdAt);
    }

    @Override
    public String toString() {
        return "DeliveryAssignedEvent{" +
                "orderId='" + orderId + '\'' +
                ", courierId='" + courierId + '\'' +
                ", etaFrom=" + etaFrom +
                ", etaTo=" + etaTo +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
