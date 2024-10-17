package com.backend.billing_and_inventory.controller;

import com.backend.billing_and_inventory.dto.ApiResponse;
import com.backend.billing_and_inventory.dto.OrderDto;
import com.backend.billing_and_inventory.exception.ResourceNotFoundException;
import com.backend.billing_and_inventory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardOrdersController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<?>> getOrdersDetailsForToday() {
        try {
            // Getting data for today
            LocalDateTime startDateAndTimeToday = LocalDateTime.now().with(LocalTime.MIN);
            LocalDateTime endDateAndTimeToday = LocalDateTime.now();

            Map<String, Object> orderDetailsForToday
                    = orderService.getOrdersCountTotalOrderAmountAndOrdersDetailsBetween(startDateAndTimeToday, endDateAndTimeToday);

            ApiResponse<Object> response = new ApiResponse<>(true, "Orders details fetched.", orderDetailsForToday);
            return ResponseEntity.ok(response);
        } catch (RuntimeException exception) {
            ApiResponse<Object> response = new ApiResponse<>(false, "Internal Server Error: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponse<Object>> getOrderDetailsById(@PathVariable("id") Long orderId) {
        try {
            OrderDto orderDetails = orderService.getOrderById(orderId);
            ApiResponse<Object> response = new ApiResponse<>(true, "Order details fetched.", orderDetails);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException exception) {
            ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Get request mapping for orders details for a particular date
    @GetMapping("/orders/date/{date}")
    public ResponseEntity<ApiResponse<Object>> getOrdersDetailsForDate(@PathVariable("date") String dateString) {
        try {
            // Define a specific date
            // LocalDate specificDate = LocalDate.of(2024, 9, 10);
            LocalDate specificDate = LocalDate.parse(dateString);

            // Create LocalDateTime with the specific date at the start of the day (00:00:00)
            LocalDateTime startDateAndTime = specificDate.atTime(LocalTime.MIN);

            // Create LocalDateTime with the specific date at the end of the day (23:59:59.999999999)
            LocalDateTime endDateAndTime = specificDate.atTime(LocalTime.MAX);

            // Print the results
            // System.out.println("Start Date and Time: " + startDateAndTime);
            // System.out.println("End Date and Time: " + endDateAndTime);

            // Getting orders details between startDateAndTime and endDateAndTime
            Map<String, Object> orderDetailsForToday
                    = orderService.getOrdersCountTotalOrderAmountAndOrdersDetailsBetween(startDateAndTime, endDateAndTime);

            // Making custom response object
            ApiResponse<Object> response = new ApiResponse<>(true, "Orders details fetched.", orderDetailsForToday);

            // Sending response
            return ResponseEntity.ok(response);
        } catch (RuntimeException exception) {
            ApiResponse<Object> response = new ApiResponse<>(false, "Internal Server Error: " + exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
