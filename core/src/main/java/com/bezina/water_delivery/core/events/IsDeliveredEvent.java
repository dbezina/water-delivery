package com.bezina.water_delivery.core.events;

import com.bezina.water_delivery.core.model.enums.OrderStatus;
import org.springframework.expression.spel.ast.Assign;

import java.util.Objects;

public class IsDeliveredEvent {
    private Long orderNo;
    private OrderStatus status; //     QUEUED,    CONFIRMED,    IN_ROUTE,    DELIVERED ,  FAILED,    CANCELLED
    private long deliveredAt;


    public IsDeliveredEvent(Long orderNo, OrderStatus status, long deliveredAt) {
        this.orderNo = orderNo;
        this.status = status;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IsDeliveredEvent that = (IsDeliveredEvent) o;
        return deliveredAt == that.deliveredAt && Objects.equals(orderNo, that.orderNo) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, status, deliveredAt);
    }

    @Override
    public String toString() {
        return "IsDeliveredEvent{" +
                "orderNo=" + orderNo +
                ", status=" + status +
                ", deliveredAt=" + deliveredAt +
                '}';
    }
}
