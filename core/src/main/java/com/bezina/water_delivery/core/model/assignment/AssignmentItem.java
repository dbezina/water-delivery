package com.bezina.water_delivery.core.model.assignment;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class AssignmentItem {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String size;
    private int quantity;

    @ManyToOne
    private AssignmentDetails assignmentDetails;

    public AssignmentItem(String id, String size, int quantity, AssignmentDetails assignmentDetails) {
        this.id = id;
        this.size = size;
        this.quantity = quantity;
        this.assignmentDetails = assignmentDetails;
    }

    public AssignmentItem() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public AssignmentDetails getAssignmentDetails() {
        return assignmentDetails;
    }

    public void setAssignmentDetails(AssignmentDetails assignmentDetails) {
        this.assignmentDetails = assignmentDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentItem that = (AssignmentItem) o;
        return quantity == that.quantity && Objects.equals(id, that.id) && Objects.equals(size, that.size) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, size, quantity, assignmentDetails);
    }

    @Override
    public String toString() {
        return "AssignmentItem{" +
                "id='" + id + '\'' +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}