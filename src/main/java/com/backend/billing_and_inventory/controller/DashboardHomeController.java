package com.backend.billing_and_inventory.controller;

import com.backend.billing_and_inventory.dto.ApiResponse;
import com.backend.billing_and_inventory.dto.OrderDto;
import com.backend.billing_and_inventory.dto.ProductDto;
import com.backend.billing_and_inventory.service.OrderService;
import com.backend.billing_and_inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardHomeController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/home")
    public ResponseEntity<ApiResponse<?>> getInventoryDetails() {
        try {
            Map<String, Map<String, Object>> data = new HashMap<>();

            // Getting data for today
            LocalDateTime startDateAndTimeToday = LocalDateTime.now().with(LocalTime.MIN);
            LocalDateTime endDateAndTimeToday = LocalDateTime.now();
            Map<String, Object> orderDetailsForToday
                    = orderService.getOrdersCountAndTotalOrderAmountBetween(startDateAndTimeToday, endDateAndTimeToday);

            data.put("orderDetailsForToday", orderDetailsForToday);

            // Getting data for the month
            LocalDateTime startDateAndTimeThisMonth = LocalDateTime.now().withDayOfMonth(1).with(LocalTime.MIN);
            LocalDateTime endDateAndTimeThisMonth = LocalDateTime.now();
            Map<String, Object> orderDetailsForThisMonth
                    = orderService.getOrdersCountAndTotalOrderAmountBetween(startDateAndTimeThisMonth, endDateAndTimeThisMonth);

            data.put("orderDetailsForThisMonth", orderDetailsForThisMonth);

            ApiResponse<Object> response = new ApiResponse<>(true, "Orders details fetched.", data);
            return ResponseEntity.ok(response);
        } catch (RuntimeException exception) {
            ApiResponse<Object> response = new ApiResponse<>(false, "Internal Server Error: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
