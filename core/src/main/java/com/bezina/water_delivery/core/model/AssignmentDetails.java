package com.bezina.water_delivery.core.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class AssignmentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @Column(nullable = false)
    private String userId;

    @OneToMany(mappedBy = "assignmentDetails", cascade = CascadeType.ALL)
    private List<AssignmentItem> items;

    @Column(nullable = false)
    private String address;

    public AssignmentDetails() {
    }

    public AssignmentDetails(String id, Assignment assignment, String userId, List<AssignmentItem> items, String address) {
        this.id = id;
        this.assignment = assignment;
        this.userId = userId;
        this.items = items;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AssignmentItem> getItems() {
        return items;
    }

    public void setItems(List<AssignmentItem> items) {
        this.items = items;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignmentDetails that = (AssignmentDetails) o;
        return Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, address);
    }

    @Override
    public String toString() {
        return "AssignmentDetails{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
