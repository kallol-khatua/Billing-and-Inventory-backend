package com.backend.billing_and_inventory.service.impl;

import com.backend.billing_and_inventory.dto.OrderDto;
import com.backend.billing_and_inventory.dto.OrderItemDto;
import com.backend.billing_and_inventory.mapper.OrderItemMapper;
import com.backend.billing_and_inventory.mapper.OrderMapper;
import com.backend.billing_and_inventory.model.Order;
import com.backend.billing_and_inventory.model.OrderItem;
import com.backend.billing_and_inventory.model.Product;
import com.backend.billing_and_inventory.repository.OrderItemRepository;
import com.backend.billing_and_inventory.repository.ProductRepository;
import com.backend.billing_and_inventory.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderItem addOrderItem(OrderItemDto orderItemDto, Order order) {
//        TODO: DUE:
//        validate data

        Product product = orderItemDto.getProduct();
        product.setQuantity(product.getQuantity() - orderItemDto.getOrderQuantity());

        productRepository.save(product);

        OrderItem newOrderItem = OrderItemMapper.mapToOrderItem(orderItemDto);
        newOrderItem.setOrder(order);

        return orderItemRepository.save(newOrderItem);
    }
}
