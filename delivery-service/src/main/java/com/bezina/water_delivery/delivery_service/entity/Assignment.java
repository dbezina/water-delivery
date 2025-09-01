package com.bezina.water_delivery.delivery_service.entity;

import com.bezina.water_delivery.delivery_service.entity.enums.AssignmentStatus;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String orderId;

    private String courierId; // может быть NULL при автопланировании

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status;

    private Instant etaFrom;
    private Instant etaTo;

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = Instant.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = Instant.now();
    }

    public Assignment(String id,
                      String orderId,
                      String courierId,
                      AssignmentStatus status,
                      Instant etaFrom,
                      Instant etaTo,
                      Instant createdAt,
                      Instant updatedAt) {
        this.id = id;
        this.orderId = orderId;
        this.courierId = courierId;
        this.status = status;
        this.etaFrom = etaFrom;
        this.etaTo = etaTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Assignment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    public Instant getEtaFrom() {
        return etaFrom;
    }

    public void setEtaFrom(Instant etaFrom) {
        this.etaFrom = etaFrom;
    }

    public Instant getEtaTo() {
        return etaTo;
    }

    public void setEtaTo(Instant etaTo) {
        this.etaTo = etaTo;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return Objects.equals(id, that.id) && Objects.equals(orderId, that.orderId) && Objects.equals(courierId, that.courierId) && status == that.status && Objects.equals(etaFrom, that.etaFrom) && Objects.equals(etaTo, that.etaTo) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, courierId, status, etaFrom, etaTo, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id='" + id + '\'' +
                ", orderId='" + orderId + '\'' +
                ", courierId='" + courierId + '\'' +
                ", status=" + status +
                ", etaFrom=" + etaFrom +
                ", etaTo=" + etaTo +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
