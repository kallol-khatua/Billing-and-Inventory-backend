package com.backend.billing_and_inventory.mapper;

import com.backend.billing_and_inventory.dto.OrderItemDto;
import com.backend.billing_and_inventory.model.OrderItem;

public class OrderItemMapper {
    public static OrderItem mapToOrderItem(OrderItemDto orderItemDto) {
        return new OrderItem(
                orderItemDto.getId(),
                orderItemDto.getProductName(),
                orderItemDto.getProductPrice(),
                orderItemDto.getOrderQuantity(),
                orderItemDto.getTotalPrice(),
                orderItemDto.getProduct(),
                orderItemDto.getOrder(),
                orderItemDto.getCreatedAt(),
                orderItemDto.getUpdatedAt()
        );
    }

    public static OrderItemDto mapToOrderItemDto(OrderItem orderItem) {
        return new OrderItemDto(
                orderItem.getId(),
                orderItem.getProductName(),
                orderItem.getProductPrice(),
                orderItem.getOrderQuantity(),
                orderItem.getTotalPrice(),
                orderItem.getProduct(),
                orderItem.getOrder(),
                orderItem.getCreatedAt(),
                orderItem.getUpdatedAt()
        );
    }
}
