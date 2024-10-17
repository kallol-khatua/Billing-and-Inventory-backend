package com.backend.billing_and_inventory.service.impl;

import com.backend.billing_and_inventory.dto.ProductDto;
import com.backend.billing_and_inventory.exception.BadRequestException;
import com.backend.billing_and_inventory.exception.ResourceNotFoundException;
import com.backend.billing_and_inventory.mapper.ProductMapper;
import com.backend.billing_and_inventory.model.Product;
import com.backend.billing_and_inventory.model.User;
import com.backend.billing_and_inventory.repository.ProductRepository;
import com.backend.billing_and_inventory.repository.UserRepository;
import com.backend.billing_and_inventory.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public User getUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        User user = getUserFromAuthentication();

        if (productDto.getName() == null || productDto.getName().isEmpty()) {
            throw new BadRequestException("Product name is required.");
        }
        if (productDto.getPrice() == null) {
            throw new BadRequestException("Product price is required.");
        }
        if (productDto.getQuantity() == null) {
            throw new BadRequestException("Product quantity is required.");
        }

        Product newProduct = ProductMapper.mapToProduct(productDto);
        newProduct.setVendor(user);

        Product savedProduct = productRepository.save(newProduct);
        return ProductMapper.mapToProductDto(savedProduct);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        User user = getUserFromAuthentication();

        List<Product> products = user.getProducts();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product : products) {
            productDtos.add(ProductMapper.mapToProductDto(product));
        }
        return productDtos;
    }


    @Override
    public ProductDto getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        return ProductMapper.mapToProductDto(product);
    }

    @Override
    public void deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
        if (product != null) {
            productRepository.delete(product);
        }
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

//        TODO: DUE:
//        while no name and no price and no quantity then 400 bad request send

        if (productDto.getName() != null && !productDto.getName().isEmpty()) {
            product.setName(productDto.getName());
        }
        if (productDto.getPrice() != null) {
            product.setPrice(productDto.getPrice());
        }
        if (productDto.getQuantity() != null) {
            product.setQuantity(productDto.getQuantity());
        }

        Product updatedProduct = productRepository.save(product);

        return ProductMapper.mapToProductDto(updatedProduct);
    }
}
