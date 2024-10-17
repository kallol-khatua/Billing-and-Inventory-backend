package com.backend.billing_and_inventory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateOrderDto {
    private List<OrderItemDto> orderItems;
    private OrderDto order;
}