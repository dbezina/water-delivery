package com.bezina.water_delivery.core.events;

import jakarta.persistence.criteria.CriteriaBuilder;

import java.time.Instant;
import java.util.Objects;

public class StockInsufficientEvent {
    private String orderId;
    private Long orderNo;
    private String reason;
    private Instant failedAt;

    public StockInsufficientEvent(String orderId, Long orderNo, String reason, Instant failedAt) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.reason = reason;
        this.failedAt = failedAt;
    }

    public StockInsufficientEvent() {
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

    public Instant getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(Instant failedAt) {
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
        StockInsufficientEvent that = (StockInsufficientEvent) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(orderNo, that.orderNo) && Objects.equals(reason, that.reason) && Objects.equals(failedAt, that.failedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderNo, reason, failedAt);
    }

    @Override
    public String toString() {
        return "StockInsufficientEvent{" +
                "orderId='" + orderId + '\'' +
                ", orderNo=" + orderNo +
                ", reason='" + reason + '\'' +
                ", failedAt=" + failedAt +
                '}';
    }
}