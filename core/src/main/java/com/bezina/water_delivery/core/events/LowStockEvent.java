package com.bezina.water_delivery.core.events;

import java.time.Instant;
import java.util.Objects;

public class LowStockEvent {
    private String size;   // например "18L"
    private String message;
    private Instant createdAt;

    public LowStockEvent(String size, String message, Instant createdAt) {
        this.size = size;
        this.message = message;
        this.createdAt = createdAt;
    }

    public LowStockEvent() {
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LowStockEvent that = (LowStockEvent) o;
        return createdAt == that.createdAt && Objects.equals(size, that.size) && Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, message, createdAt);
    }

    @Override
    public String toString() {
        return "LowStockEvent{" +
                "size='" + size + '\'' +
                ", message='" + message + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
