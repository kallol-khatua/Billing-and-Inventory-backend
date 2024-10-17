package com.backend.billing_and_inventory.service;

import com.backend.billing_and_inventory.dto.OrderDto;
import com.backend.billing_and_inventory.model.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order createOrder(OrderDto orderDto);

    OrderDto getOrderById(Long id);

    Map<String, Object> getOrdersCountAndTotalOrderAmountBetween(LocalDateTime startDateAndTime, LocalDateTime endDateAndTime);

    Map<String, Object> getOrdersCountTotalOrderAmountAndOrdersDetailsBetween(LocalDateTime startDateAndTime, LocalDateTime endDateAndTime);

    BigDecimal getTotalOrderAmountForToday();

    BigDecimal getTotalOrderAmountForThisMonth();
}
