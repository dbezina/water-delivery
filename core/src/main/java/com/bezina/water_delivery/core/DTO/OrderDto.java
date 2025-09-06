package com.bezina.water_delivery.core.DTO;

import com.bezina.water_delivery.core.model.orders.Order;
import com.bezina.water_delivery.core.model.enums.OrderStatus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderDto {
    private String id;
    private Long no;
    private String userId;
    private String address;
    private OrderStatus currentStatus;
    private List<OrderItemDto> items;

    public static OrderDto fromEntity(Order order) {
        return new OrderDto(order.getId(), order.getOrderNo(), order.getUserId(), order.getAddress(), order.getStatus(),
                order.getItems().stream()
                        .map(i -> new OrderItemDto(i.getSize(), i.getQuantity()))
                        .collect(Collectors.toList()));
    }

    public OrderDto(String id,Long orderNo, String userId, String address, OrderStatus currentStatus,List<OrderItemDto> items) {
        this.id = id;
        this.no = orderNo;
        this.userId = userId;
        this.address = address;
        this.currentStatus = currentStatus;
        this.items = items;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }

    public OrderDto() {
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public OrderStatus getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(OrderStatus currentStatus) {
        this.currentStatus = currentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(id, orderDto.id) && Objects.equals(no, orderDto.no) && Objects.equals(userId, orderDto.userId) && Objects.equals(address, orderDto.address) && currentStatus == orderDto.currentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, no, userId, address, currentStatus);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id='" + id + '\'' +
                ", no=" + no +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                ", currentStatus=" + currentStatus +
                '}';
    }
}
