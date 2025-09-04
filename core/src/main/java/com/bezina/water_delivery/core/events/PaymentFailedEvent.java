package com.bezina.water_delivery.core.events;

import java.util.Objects;

public class PaymentFailedEvent {
    private String orderId;
    private Long orderNo;
    private String reason;
    private long failedAt;

    public PaymentFailedEvent(String orderId, Long orderNo, String reason, long failedAt) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.reason = reason;
        this.failedAt = failedAt;
    }

    public PaymentFailedEvent() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public long getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(long failedAt) {
        this.failedAt = failedAt;
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
        PaymentFailedEvent that = (PaymentFailedEvent) o;
        return failedAt == that.failedAt && Objects.equals(orderId, that.orderId) && Objects.equals(orderNo, that.orderNo) && Objects.equals(reason, that.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderNo, reason, failedAt);
    }

    @Override
    public String toString() {
        return "PaymentFailedEvent{" +
                "orderId='" + orderId + '\'' +
                ", orderNo=" + orderNo +
                ", reason='" + reason + '\'' +
                ", failedAt=" + failedAt +
                '}';
    }
}
