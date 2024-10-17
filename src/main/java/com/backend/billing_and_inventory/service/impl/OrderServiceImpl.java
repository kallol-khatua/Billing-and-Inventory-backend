package com.backend.billing_and_inventory.service.impl;

import com.backend.billing_and_inventory.dto.OrderDto;
import com.backend.billing_and_inventory.exception.ResourceNotFoundException;
import com.backend.billing_and_inventory.mapper.OrderMapper;
import com.backend.billing_and_inventory.model.Order;
import com.backend.billing_and_inventory.model.User;
import com.backend.billing_and_inventory.repository.OrderRepository;
import com.backend.billing_and_inventory.repository.UserRepository;
import com.backend.billing_and_inventory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    public User getUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }


    public static String generateOrderId() {
        // Step 1: Prefix
        String prefix = "OD";

        // Step 2: Get the current date-time and format it
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);

        // Step 3: Generate an 8-character hash based on the formatted date-time string
        String hash = generateAlphaNumericHash(formattedDateTime, 8);

        // Combine prefix and hash to create the order ID
        return prefix + hash.toUpperCase();
    }

    private static String generateAlphaNumericHash(String input, int length) {
        try {
            // Generate SHA-256 hash of the input
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(input.getBytes());

            // Convert hash bytes to a hexadecimal string
            String alphanumericString = getString(hashBytes);

            // Ensure the output is at least as long as the required length
            if (alphanumericString.length() < length) {
                throw new RuntimeException("Generated hash is too short for the desired length.");
            }

            // Return the first 'length' characters of the alphanumeric string
            return alphanumericString.substring(0, length);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating hash", e);
        }
    }

    private static String getString(byte[] hashBytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        // Convert hex string to alphanumeric string and extract only letters and digits
        return hexString.toString().replaceAll("[^A-Za-z0-9]", "");
    }

    @Override
    public Order createOrder(OrderDto orderDto) {
        User user = getUserFromAuthentication();
//        TODO: DUE:
//        validate data

        Order newOrder = OrderMapper.mapToOrder(orderDto);
        newOrder.setVendor(user);
//        TODO: DUE:
//        handle exception while generating order id
        newOrder.setOrderId(generateOrderId());

        return orderRepository.save(newOrder);
    }

    @Override
    public OrderDto getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return OrderMapper.mapToOrderDto(order);
    }

    @Override
    public Map<String, Object> getOrdersCountAndTotalOrderAmountBetween(LocalDateTime startDateAndTime, LocalDateTime endDateAndTime) {
        User user = getUserFromAuthentication();

        // Find orders created by the user within the time range
        List<Order> orders = orderRepository.findByCreatedAtBetweenAndVendor(startDateAndTime, endDateAndTime, user);

        BigDecimal totalOrderAmount = BigDecimal.ZERO;
        int totalOrderCount = 0;

        for (Order order : orders) {
            totalOrderAmount = totalOrderAmount.add(order.getTotalAmount());
            totalOrderCount++;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("totalOrderAmount", totalOrderAmount);
        data.put("totalOrderCount", totalOrderCount);

        return data;
    }

    @Override
    public Map<String, Object> getOrdersCountTotalOrderAmountAndOrdersDetailsBetween(LocalDateTime startDateAndTime, LocalDateTime endDateAndTime) {
        User user = getUserFromAuthentication();

        // Find orders created by the user within the time range
        List<Order> orders = orderRepository.findByCreatedAtBetweenAndVendor(startDateAndTime, endDateAndTime, user);

        BigDecimal totalOrderAmount = BigDecimal.ZERO;
        int totalOrderCount = 0;

        List<OrderDto> orderDtoList = new ArrayList<>();

        for (Order order : orders) {
            totalOrderAmount = totalOrderAmount.add(order.getTotalAmount());
            totalOrderCount++;
            orderDtoList.add(OrderMapper.mapToOrderDto(order));
        }

        Map<String, Object> data = new HashMap<>();
        data.put("totalOrderAmount", totalOrderAmount);
        data.put("totalOrderCount", totalOrderCount);
        data.put("orderDetails", orderDtoList);

        return data;
    }

    @Override
    public BigDecimal getTotalOrderAmountForToday() {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        return orderRepository.findTotalOrderAmountSumForToday(startOfDay, endOfDay);
    }

    public BigDecimal getTotalOrderAmountForThisMonth() {
//        change the date and time to get info for the month
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);

        return orderRepository.findTotalOrderAmountSumForToday(startOfDay, endOfDay);
    }
}
