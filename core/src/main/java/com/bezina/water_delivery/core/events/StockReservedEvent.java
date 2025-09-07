package com.bezina.water_delivery.core.events;

import com.bezina.water_delivery.core.DTO.OrderItemDto;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class StockReservedEvent {
    private String orderId;
    private Long orderNo;
    private String userId;
    private List<OrderItemDto> items;
    private String address;
    private Instant createdAt;

    public StockReservedEvent() {
    }

    public StockReservedEvent(String orderId,  Long orderNo, String userId, List<OrderItemDto> items, String address, Instant createdAt) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.userId = userId;
        this.items = items;
        this.address = address;
        this.createdAt = createdAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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
        StockReservedEvent that = (StockReservedEvent) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(orderNo, that.orderNo) && Objects.equals(userId, that.userId) && Objects.equals(items, that.items) && Objects.equals(address, that.address) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderNo, userId, items, address, createdAt);
    }

    @Override
    public String toString() {
        return "StockReservedEvent{" +
                "orderId='" + orderId + '\'' +
                ", orderNo=" + orderNo +
                ", userId='" + userId + '\'' +
                ", items=" + items +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}