package com.bezina.water_delivery.core.events;



import com.bezina.water_delivery.core.DTO.OrderItemDto;

import java.util.List;
import java.util.Objects;

    public class OrderCreatedEvent {
        private String orderId;
        private String userId;
        private List<OrderItemDto> items;
        private String address;
        private long createdAt;


        public OrderCreatedEvent() {
    }

    public OrderCreatedEvent(String orderId, String userId, List<OrderItemDto> items, String address, Long createdAt) {
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId='" + orderId + '\'' +
                ", userId='" + userId + '\'' +
                ", items=" + items +
                ", address='" + address + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderCreatedEvent that = (OrderCreatedEvent) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(userId, that.userId) && Objects.equals(items, that.items) && Objects.equals(address, that.address) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, items, address, createdAt);
    }
}

