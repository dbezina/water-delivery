package com.bezina.water_delivery.order_service.DTO;

import com.bezina.water_delivery.core.DTO.OrderItemDto;

import java.util.List;
import java.util.Objects;

public class CreateOrderRequest {
    private String userId;
    private String address;
    private List<OrderItemDto> items;

    public CreateOrderRequest(String userId, String address, List<OrderItemDto> items) {
        this.userId = userId;
        this.address = address;
        this.items = items;
    }

    public CreateOrderRequest() {
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

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateOrderRequest that = (CreateOrderRequest) o;
        return Objects.equals(userId, that.userId) && Objects.equals(address, that.address) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, address, items);
    }

    @Override
    public String toString() {
        return "CreateOrderRequest{" +
                "userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                ", items=" + items +
                '}';
    }
}

