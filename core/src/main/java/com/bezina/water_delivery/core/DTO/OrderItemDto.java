package com.bezina.water_delivery.core.DTO;

import java.util.Objects;


public class OrderItemDto {
    private String size;
    private int quantity;

    public OrderItemDto(String size, int quantity) {
        this.size = size;
        this.quantity = quantity;
    }

    public OrderItemDto() {
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderItemDto that = (OrderItemDto) o;
        return quantity == that.quantity && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, quantity);
    }

    @Override
    public String toString() {
        return "OrderItemDto{" +
                "size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}