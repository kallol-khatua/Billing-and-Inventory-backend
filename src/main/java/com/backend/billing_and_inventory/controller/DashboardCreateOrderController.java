package com.backend.billing_and_inventory.controller;

import com.backend.billing_and_inventory.dto.*;
import com.backend.billing_and_inventory.exception.BadRequestException;
import com.backend.billing_and_inventory.exception.ResourceNotFoundException;
import com.backend.billing_and_inventory.model.Order;
import com.backend.billing_and_inventory.service.OrderItemService;
import com.backend.billing_and_inventory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardCreateOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @PostMapping("/create-order")
    public ResponseEntity<ApiResponse<?>> addNewProduct(@RequestBody(required = false) CreateOrderDto createOrderDto) {
        try {
//            TODO: DUE:
//            add validation at least one order item

            // Creating order
            Order order = orderService.createOrder(createOrderDto.getOrder());

            // Adding order items
            for (OrderItemDto orderItem : createOrderDto.getOrderItems()) {
                orderItemService.addOrderItem(orderItem, order);
            }

            // Getting order details
            OrderDto orderDto = orderService.getOrderById(order.getId());

            ApiResponse<Object> response = new ApiResponse<>(true, "Order created successfully.", orderDto);
            return ResponseEntity.ok(response);
        } catch (BadRequestException | ResourceNotFoundException exception) {
            ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
