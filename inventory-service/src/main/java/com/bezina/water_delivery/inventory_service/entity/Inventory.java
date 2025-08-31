package com.bezina.water_delivery.inventory_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Inventory {
    @Id
    private String size;   // "1L", "5L", "18L"
    private int quantity;  // сколько бутылей доступно

    public Inventory() {
    }

    public Inventory(String size, int quantity) {
        this.size = size;
        this.quantity = quantity;
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
