package com.bezina.water_delivery.inventory_service.entity;

import java.util.Objects;

public class RestockRequest {
    private String size;     // "1L", "5L", "18L"
    private int quantity;

    public RestockRequest(String size, int quantity) {
        this.size = size;
        this.quantity = quantity;
    }

    public RestockRequest() {
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
        RestockRequest that = (RestockRequest) o;
        return quantity == that.quantity && Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, quantity);
    }

    @Override
    public String toString() {
        return "RestockRequest{" +
                "size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
