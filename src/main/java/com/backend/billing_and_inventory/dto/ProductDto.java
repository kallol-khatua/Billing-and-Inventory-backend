package com.backend.billing_and_inventory.dto;

import com.backend.billing_and_inventory.model.User;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private User vendor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
