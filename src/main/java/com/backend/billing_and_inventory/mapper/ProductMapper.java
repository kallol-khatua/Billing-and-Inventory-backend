package com.backend.billing_and_inventory.mapper;

import com.backend.billing_and_inventory.dto.ProductDto;
import com.backend.billing_and_inventory.model.Product;

public class ProductMapper {
    public static Product mapToProduct(ProductDto productDto) {
        return new Product(
                productDto.getId(),
                productDto.getName(),
                productDto.getPrice(),
                productDto.getQuantity(),
                productDto.getVendor(),
                productDto.getCreatedAt(),
                productDto.getUpdatedAt()
        );
    }

    public static ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantity(),
                product.getVendor(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
