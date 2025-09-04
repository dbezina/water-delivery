package com.bezina.water_delivery.core.DTO;



import com.bezina.water_delivery.core.model.Assignment;

import java.time.Instant;
import java.util.List;

public class AssignmentDto {
    private String id;
    private Long orderNo;
    private String courierId;
    private String userId;
    private String status;
    private String address;
    private List<AssignmentItemDto> items;
    private Instant deliverFrom;
    private Instant deliverTo;

    public AssignmentDto() {
    }
    public static AssignmentDto fromEntity(Assignment assignment) {
        AssignmentDto newAssignmentDto =  new AssignmentDto();
        newAssignmentDto.setId(assignment.getId());
        newAssignmentDto.setCourierId(assignment.getCourierId());
        newAssignmentDto.setStatus(assignment.getStatus().name());
        newAssignmentDto.setOrderNo(assignment.getOrderNo());
        newAssignmentDto.setAddress(assignment.getDetails() != null ? assignment.getDetails().getAddress() : null);
        newAssignmentDto.setUserId(assignment.getDetails() != null ? assignment.getDetails().getUserId() : null);

        newAssignmentDto.setItems(assignment.getDetails() != null ?
                assignment.getDetails().getItems().stream().map(AssignmentItemDto::fromEntity).toList()
                : List.of());
        newAssignmentDto.setDeliverFrom(assignment.getDeliverFrom());
        newAssignmentDto.setDeliverTo(assignment.getDeliverTo());

        return newAssignmentDto;
    }

    public AssignmentDto(String id, Long orderNo, String courierId, String userId, String status, String address, List<AssignmentItemDto> items, Instant deliverFrom, Instant deliverTo) {
        this.id = id;
        this.orderNo = orderNo;
        this.courierId = courierId;
        this.userId = userId;
        this.status = status;
        this.address = address;
        this.items = items;
        this.deliverFrom = deliverFrom;
        this.deliverTo = deliverTo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<AssignmentItemDto> getItems() {
        return items;
    }

    public void setItems(List<AssignmentItemDto> items) {
        this.items = items;
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
}
