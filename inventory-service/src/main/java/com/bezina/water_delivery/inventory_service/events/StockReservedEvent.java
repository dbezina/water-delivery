package com.bezina.water_delivery.inventory_service.events;

import com.bezina.water_delivery.core.DTO.OrderItemDto;

import java.util.List;
import java.util.Objects;

public class StockReservedEvent {
    private String orderId;
    private String userId;
    private List<OrderItemDto> items;
    private String address;
    private long createdAt;

    public StockReservedEvent() {
    }

    public StockReservedEvent(String orderId, String userId, List<OrderItemDto> items, String address, long createdAt) {
        this.orderId = orderId;
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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StockReservedEvent that = (StockReservedEvent) o;
        return createdAt == that.createdAt && Objects.equals(orderId, that.orderId) && Objects.equals(userId, that.userId) && Objects.equals(items, that.items) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, items, address, createdAt);
    }

    @Override
    public String toString() {
        return "StockReservedEvent{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", items=" + items +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}