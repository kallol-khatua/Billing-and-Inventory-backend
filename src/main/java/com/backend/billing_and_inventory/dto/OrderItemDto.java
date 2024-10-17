package com.backend.billing_and_inventory.dto;

import com.backend.billing_and_inventory.model.Order;
import com.backend.billing_and_inventory.model.Product;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemDto {
    private Long id;
    private String productName;
    private BigDecimal productPrice;
    private Integer orderQuantity;
    private BigDecimal totalPrice;
    private Product product;
    private Order order;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
