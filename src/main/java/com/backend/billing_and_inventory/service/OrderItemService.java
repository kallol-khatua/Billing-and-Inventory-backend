package com.backend.billing_and_inventory.service;

import com.backend.billing_and_inventory.dto.OrderDto;
import com.backend.billing_and_inventory.dto.OrderItemDto;
import com.backend.billing_and_inventory.model.Order;
import com.backend.billing_and_inventory.model.OrderItem;

public interface OrderItemService {
    OrderItem addOrderItem(OrderItemDto orderItemDto, Order order);
}
