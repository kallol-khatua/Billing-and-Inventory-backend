package com.backend.billing_and_inventory.mapper;

import com.backend.billing_and_inventory.dto.OrderDto;
import com.backend.billing_and_inventory.model.Order;

public class OrderMapper {
    public static Order mapToOrder(OrderDto orderDto) {
        return new Order(
                orderDto.getId(),
                orderDto.getTotalAmount(),
                orderDto.getCustomerName(),
                orderDto.getOrderStatus(),
                orderDto.getPaymentMode(),
                orderDto.getOrderId(),
                orderDto.getOrderItems(),
                orderDto.getVendor(),
                orderDto.getCreatedAt(),
                orderDto.getUpdatedAt()
        );
    }

    public static OrderDto mapToOrderDto(Order order) {
        return new OrderDto(
                order.getId(),
                order.getTotalAmount(),
                order.getCustomerName(),
                order.getOrderStatus(),
                order.getPaymentMode(),
                order.getOrderId(),
                order.getOrderItems(),
                order.getVendor(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}
