package com.bezina.water_delivery.delivery_service.DTO;

import com.bezina.water_delivery.delivery_service.entity.AssignmentItem;

public class AssignmentItemDto {
    private String size;
    private int quantity;

    public AssignmentItemDto(String size, int quantity) {
        this.size = size;
        this.quantity = quantity;
    }

    public AssignmentItemDto() {
    }
    public static AssignmentItemDto fromEntity(AssignmentItem assignmentItem) {
        return new AssignmentItemDto(
                assignmentItem.getSize(),
                assignmentItem.getQuantity()
        );
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


}
