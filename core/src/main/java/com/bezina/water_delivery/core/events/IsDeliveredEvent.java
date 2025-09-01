package com.bezina.water_delivery.core.events;

import java.util.Objects;

public class IsDeliveredEvent {
    private String orderId;
    private long deliveredAt;

    public IsDeliveredEvent(String orderId, long deliveredAt) {
        this.orderId = orderId;
        this.deliveredAt = deliveredAt;
    }

    public IsDeliveredEvent() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(long deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IsDeliveredEvent that = (IsDeliveredEvent) o;
        return deliveredAt == that.deliveredAt && Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, deliveredAt);
    }

    @Override
    public String toString() {
        return "IsDeliveredEvent{" +
                "orderId='" + orderId + '\'' +
                ", deliveredAt=" + deliveredAt +
                '}';
    }
}
