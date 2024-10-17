package com.backend.billing_and_inventory.service;

import com.backend.billing_and_inventory.dto.ProductDto;
import com.backend.billing_and_inventory.model.Product;

import java.util.List;

public interface ProductService {
    ProductDto addProduct(ProductDto productDto);

    ProductDto getProductById(Long productId);

    List<ProductDto> getAllProducts();

    void deleteProductById(Long productId);

    ProductDto updateProduct(Long productId, ProductDto productDto);
}
