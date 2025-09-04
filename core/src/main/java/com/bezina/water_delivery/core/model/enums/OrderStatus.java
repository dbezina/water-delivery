package com.bezina.water_delivery.core.model.enums;

public enum OrderStatus {
    QUEUED,      // заявка принята
    CONFIRMED,   // подтверждено
    IN_ROUTE,    // в пути
    DELIVERED ,   // доставлено
    FAILED,    //не удалось вручить
    CANCELLED
}