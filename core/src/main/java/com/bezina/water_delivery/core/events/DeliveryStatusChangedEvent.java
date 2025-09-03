package com.bezina.water_delivery.core.events;

import com.bezina.water_delivery.core.model.OrderStatus;

import java.util.Objects;

public class DeliveryStatusChangedEvent {
    private Long orderNo;
    private OrderStatus status; // PENDING, CONFIRMED, IN_ROUTE, DELIVERED, CANCELLED
    private long changedAt;

    public DeliveryStatusChangedEvent(Long orderNo, OrderStatus status, long changedAt) {
        this.orderNo = orderNo;
        this.status = status;
        this.changedAt = changedAt;
    }

    public DeliveryStatusChangedEvent() {
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
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
        return changedAt == that.changedAt && Objects.equals(orderNo, that.orderNo) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, status, changedAt);
    }

    @Override
    public String toString() {
        return "DeliveryStatusChangedEvent{" +
                "orderId='" + orderNo + '\'' +
                ", status=" + status +
                ", changedAt=" + changedAt +
                '}';
    }
}
