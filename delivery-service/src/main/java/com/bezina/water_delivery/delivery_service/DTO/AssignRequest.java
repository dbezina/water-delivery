package com.bezina.water_delivery.delivery_service.DTO;

import java.util.Objects;

public class AssignRequest {
    private String orderId;
    private String courierId;

    public AssignRequest(String orderId, String courierId) {
        this.orderId = orderId;
        this.courierId = courierId;
    }

    public AssignRequest() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignRequest that = (AssignRequest) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(courierId, that.courierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, courierId);
    }

    @Override
    public String toString() {
        return "AssignRequest{" +
                "orderId='" + orderId + '\'' +
                ", courierId='" + courierId + '\'' +
                '}';
    }
}
