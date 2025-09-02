package com.bezina.water_delivery.core.DTO;

import com.bezina.water_delivery.core.model.Order;
import com.bezina.water_delivery.core.model.OrderStatus;

import java.util.Objects;

public class OrderDto {
    private String id;
    private String userId;
    private String address;
    private OrderStatus currentStatus;

    public static OrderDto fromEntity(Order order) {
        return new OrderDto(order.getId(), order.getUserId(), order.getAddress(), order.getStatus());
    }

    public OrderDto(String id, String userId, String address, OrderStatus currentStatus) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.currentStatus = currentStatus;
    }

    public OrderDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(OrderStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id) && Objects.equals(userId, orderDto.userId) && Objects.equals(address, orderDto.address) && currentStatus == orderDto.currentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, address, currentStatus);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                ", currentStatus=" + currentStatus +
                '}';
    }
}
