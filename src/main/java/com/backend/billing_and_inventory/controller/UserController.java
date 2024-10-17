package com.backend.billing_and_inventory.controller;

import com.backend.billing_and_inventory.dto.ApiResponse;
import com.backend.billing_and_inventory.dto.UserDto;
import com.backend.billing_and_inventory.dto.VerifyUserDto;
import com.backend.billing_and_inventory.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public String helloWorld(HttpServletRequest request) {
        return "Hello World " + request.getSession().getId();
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody UserDto user) {
        UserDto registeredUser = userService.registerUser(user);

        ApiResponse<Object> response = new ApiResponse<>(true, "User registered successfully.", registeredUser);

        // Return the response with 201 Created status
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> verifyUser(@RequestBody VerifyUserDto user) {
        String token = userService.verifyUser(user);

        Map<String, String> authData = new HashMap<>();
        authData.put("token", token);

        ApiResponse<Object> response = new ApiResponse<>(true, "User authenticated successfully.", authData);
        return ResponseEntity.ok(response);
    }
}
