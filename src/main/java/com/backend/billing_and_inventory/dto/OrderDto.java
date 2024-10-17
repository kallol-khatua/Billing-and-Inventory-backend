package com.backend.billing_and_inventory.dto;

import com.backend.billing_and_inventory.model.OrderItem;
import com.backend.billing_and_inventory.model.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private Long id;
    private BigDecimal totalAmount;
    private String customerName;
    private String orderStatus;
    private String paymentMode;
    private String orderId;
    private List<OrderItem> orderItems;
    private User vendor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
