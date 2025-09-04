package com.bezina.water_delivery.core.model;

import com.bezina.water_delivery.core.model.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class OrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Instant changedAt;

    public OrderStatusHistory(Long id, Order order, OrderStatus status, Instant changedAt) {
        this.id = id;
        this.order = order;
        this.status = status;
        this.changedAt = changedAt;
    }

    public OrderStatusHistory() {
    }

    @PrePersist
    public void onCreate() {
        changedAt = Instant.now();
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Instant getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Instant changedAt) {
        this.changedAt = changedAt;
    }
}
