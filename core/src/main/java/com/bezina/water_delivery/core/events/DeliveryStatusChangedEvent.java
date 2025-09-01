package com.bezina.water_delivery.core.events;

import com.bezina.water_delivery.core.model.OrderStatus;

import java.util.Objects;

public class DeliveryStatusChangedEvent {
    private String orderId;
    private OrderStatus status; // PENDING, CONFIRMED, IN_ROUTE, DELIVERED, CANCELLED
    private long changedAt;

    public DeliveryStatusChangedEvent(String orderId, OrderStatus status, long changedAt) {
        this.orderId = orderId;
        this.status = status;
        this.changedAt = changedAt;
    }

    public DeliveryStatusChangedEvent() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public long getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(long changedAt) {
        this.changedAt = changedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeliveryStatusChangedEvent that = (DeliveryStatusChangedEvent) o;
        return changedAt == that.changedAt && Objects.equals(orderId, that.orderId) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, status, changedAt);
    }

    @Override
    public String toString() {
        return "DeliveryStatusChangedEvent{" +
                "orderId='" + orderId + '\'' +
                ", status=" + status +
                ", changedAt=" + changedAt +
                '}';
    }
}
