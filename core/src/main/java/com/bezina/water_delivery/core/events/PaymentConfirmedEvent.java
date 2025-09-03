package com.bezina.water_delivery.core.events;

import java.util.Objects;

public class PaymentConfirmedEvent {
    private String orderId;
    private long confirmedAt;

    public PaymentConfirmedEvent(String orderId, long confirmedAt) {
        this.orderId = orderId;
        this.confirmedAt = confirmedAt;
    }

    public PaymentConfirmedEvent() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentConfirmedEvent that = (PaymentConfirmedEvent) o;
        return confirmedAt == that.confirmedAt && Objects.equals(orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, confirmedAt);
    }

    public long getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(long confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    @Override
    public String toString() {
        return "PaymentConfirmedEvent{" +
                "orderId='" + orderId + '\'' +
                ", confirmedAt=" + confirmedAt +
                '}';
    }
}
