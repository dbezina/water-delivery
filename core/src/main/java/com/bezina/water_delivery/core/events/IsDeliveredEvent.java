package com.bezina.water_delivery.core.events;

import java.util.Objects;

public class IsDeliveredEvent {
    private Long orderNo;
    private long deliveredAt;


    public IsDeliveredEvent(Long orderNo, long deliveredAt) {
        this.orderNo = orderNo;
        this.deliveredAt = deliveredAt;
    }

    public IsDeliveredEvent() {
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
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
        return deliveredAt == that.deliveredAt && Objects.equals(orderNo, that.orderNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, deliveredAt);
    }

    @Override
    public String toString() {
        return "IsDeliveredEvent{" +
                "orderNo=" + orderNo +
                ", deliveredAt=" + deliveredAt +
                '}';
    }
}
