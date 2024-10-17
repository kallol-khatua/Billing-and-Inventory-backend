package com.backend.billing_and_inventory.controller;

import com.backend.billing_and_inventory.dto.ApiResponse;
import com.backend.billing_and_inventory.dto.ProductDto;
import com.backend.billing_and_inventory.exception.BadRequestException;
import com.backend.billing_and_inventory.exception.ResourceNotFoundException;
import com.backend.billing_and_inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/dashboard")
public class DashboardManageInventoryController {

    @Autowired
    private ProductService productService;

    @GetMapping("/manage-inventory")
    public ResponseEntity<ApiResponse<?>> getInventoryDetails() {
        List<ProductDto> products = productService.getAllProducts();
        ApiResponse<Object> response = new ApiResponse<>(true, "Inventory details fetched.", products);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/manage-inventory")
    public ResponseEntity<ApiResponse<?>> addNewProduct(@RequestBody(required = false) ProductDto product) {
        if (product == null) {
            ApiResponse<Object> response = new ApiResponse<>(false, "Product data cannot be null.", null);
            return ResponseEntity.badRequest().body(response);
        }
        try {
            ProductDto savedProduct = productService.addProduct(product);
            ApiResponse<Object> response = new ApiResponse<>(true, "Product added successfully.", savedProduct);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(BadRequestException exception) {
            ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/manage-inventory/{id}")
    public ResponseEntity<ApiResponse<?>> getProductById(@PathVariable("id") Long productId) {
        try {
            ProductDto product = productService.getProductById(productId);
            ApiResponse<Object> response = new ApiResponse<>(true, "Product found.", product);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException exception) {
            ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @DeleteMapping("/manage-inventory/{id}")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable("id") Long productId) {
        try {
            productService.deleteProductById(productId);
            ApiResponse<Object> response = new ApiResponse<>(true, "Product removed successfully.", 1);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException exception) {
            ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PutMapping("/manage-inventory/{id}")
    public ResponseEntity<ApiResponse<?>> updateProductDetails(@PathVariable("id") Long productId, @RequestBody(required = false) ProductDto productDto) {
        if (productDto == null) {
            ApiResponse<Object> response = new ApiResponse<>(false, "Product data to update cannot be null.", null);
            return ResponseEntity.badRequest().body(response);
        }
        try {
            ProductDto updatedProduct = productService.updateProduct(productId, productDto);
            ApiResponse<Object> response = new ApiResponse<>(true, "Product Updated successfully.", updatedProduct);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException exception) {
            ApiResponse<Object> response = new ApiResponse<>(false, exception.getMessage(), null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
