package com.bezina.water_delivery.core.events;


import com.bezina.water_delivery.core.DTO.AssignmentItemDto;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public class CourierAssignmentEvent {

    private String assignmentId;
    private Long orderNo;
    private String courierId;
    private String userId;
    private List<AssignmentItemDto> items;
    private String address;
    private Instant deliverFrom;
    private Instant deliverTo;

    public CourierAssignmentEvent() {    }

    public CourierAssignmentEvent(String assignmentId, Long orderNo, String courierId,
                                  String userId, List<AssignmentItemDto> items, String address,
                                  Instant deliverFrom, Instant deliverTo) {
        this.assignmentId = assignmentId;
        this.orderNo = orderNo;
        this.courierId = courierId;
        this.userId = userId;
        this.items = items;
        this.address = address;
        this.deliverFrom = deliverFrom;
        this.deliverTo = deliverTo;
    }

    public String getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(String assignmentId) {
        this.assignmentId = assignmentId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AssignmentItemDto> getItems() {
        return items;
    }

    public void setItems(List<AssignmentItemDto> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getDeliverFrom() {
        return deliverFrom;
    }

    public void setDeliverFrom(Instant deliverFrom) {
        this.deliverFrom = deliverFrom;
    }

    public Instant getDeliverTo() {
        return deliverTo;
    }

    public void setDeliverTo(Instant deliverTo) {
        this.deliverTo = deliverTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourierAssignmentEvent that = (CourierAssignmentEvent) o;
        return Objects.equals(assignmentId, that.assignmentId) && Objects.equals(orderNo, that.orderNo) && Objects.equals(courierId, that.courierId) && Objects.equals(userId, that.userId) && Objects.equals(items, that.items) && Objects.equals(address, that.address) && Objects.equals(deliverFrom, that.deliverFrom) && Objects.equals(deliverTo, that.deliverTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentId, orderNo, courierId, userId, items, address, deliverFrom, deliverTo);
    }

    @Override
    public String toString() {
        return "CourierAssignmentEvent{" +
                "assignmentId='" + assignmentId + '\'' +
                ", orderNo=" + orderNo +
                ", courierId='" + courierId + '\'' +
                ", userId='" + userId + '\'' +
                ", items=" + items +
                ", address='" + address + '\'' +
                ", deliverFrom=" + deliverFrom +
                ", deliverTo=" + deliverTo +
                '}';
    }
}