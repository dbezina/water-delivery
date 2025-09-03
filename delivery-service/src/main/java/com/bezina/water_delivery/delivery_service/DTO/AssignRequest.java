package com.bezina.water_delivery.delivery_service.DTO;

import java.util.Objects;

public class AssignRequest {
    private Long orderNo;
    private String courierId;

    public AssignRequest(Long orderNo, String courierId) {
        this.orderNo = orderNo;
        this.courierId = courierId;
    }

    public AssignRequest() {
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
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
        return Objects.equals(orderNo, that.orderNo) && Objects.equals(courierId, that.courierId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, courierId);
    }

    @Override
    public String toString() {
        return "AssignRequest{" +
                "orderNo=" + orderNo +
                ", courierId='" + courierId + '\'' +
                '}';
    }
}
