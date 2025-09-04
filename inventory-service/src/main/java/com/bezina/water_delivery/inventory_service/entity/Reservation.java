package com.bezina.water_delivery.inventory_service.entity;

import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
public class Reservation {
    @Id
    private Long orderNo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "reservation_items", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyColumn(name = "item_size")
    @Column(name = "quantity")
    private Map<String, Integer> items = new HashMap<>();

    public Reservation() {
    }

    public Reservation(Long orderNo, Map<String, Integer> items) {
        this.orderNo = orderNo;
        this.items = items;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public Map<String, Integer> getItems() {
        return items;
    }

    public void setItems(Map<String, Integer> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(orderNo, that.orderNo) && Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNo, items);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "orderId='" + orderNo + '\'' +
                ", items=" + items +
                '}';
    }
}
