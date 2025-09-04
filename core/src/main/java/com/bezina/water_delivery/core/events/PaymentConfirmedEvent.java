package com.bezina.water_delivery.core.events;

import java.util.Objects;

public class PaymentConfirmedEvent {
    private String orderId;
    private Long orderNo;
    private long confirmedAt;

    public PaymentConfirmedEvent(String orderId, Long orderNo, long confirmedAt) {
        this.orderId = orderId;
        this.orderNo = orderNo;
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

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentConfirmedEvent that = (PaymentConfirmedEvent) o;
        return confirmedAt == that.confirmedAt && Objects.equals(orderId, that.orderId) && Objects.equals(orderNo, that.orderNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderNo, confirmedAt);
    }

    @Override
    public String toString() {
        return "PaymentConfirmedEvent{" +
                "orderId='" + orderId + '\'' +
                ", orderNo=" + orderNo +
                ", confirmedAt=" + confirmedAt +
                '}';
    }
}
