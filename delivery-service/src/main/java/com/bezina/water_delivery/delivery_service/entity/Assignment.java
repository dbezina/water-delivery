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
    private Long orderNo;

    private String courierId; // может быть NULL при автопланировании

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AssignmentStatus status;

    private Instant deliverFrom;
    private Instant deliverTo;

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
                      Long orderNo,
                      String courierId,
                      AssignmentStatus status,
                      Instant deliverFrom,
                      Instant deliverTo,
                      Instant createdAt,
                      Instant updatedAt) {
        this.id = id;
        this.orderNo = orderNo;
        this.courierId = courierId;
        this.status = status;
        this.deliverFrom = deliverFrom;
        this.deliverTo = deliverTo;
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

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
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
        return Objects.equals(id, that.id) && Objects.equals(orderNo, that.orderNo) && Objects.equals(courierId, that.courierId) && status == that.status && Objects.equals(deliverFrom, that.deliverFrom) && Objects.equals(deliverTo, that.deliverTo) && Objects.equals(createdAt, that.createdAt) && Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNo, courierId, status, deliverFrom, deliverTo, createdAt, updatedAt);
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "id='" + id + '\'' +
                ", orderId='" + orderNo + '\'' +
                ", courierId='" + courierId + '\'' +
                ", status=" + status +
                ", etaFrom=" + deliverFrom +
                ", etaTo=" + deliverTo +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
