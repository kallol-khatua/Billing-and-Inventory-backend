package com.backend.billing_and_inventory.repository;

import com.backend.billing_and_inventory.model.Order;
import com.backend.billing_and_inventory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Order> findByCreatedAtBetweenAndVendor(LocalDateTime startDateAndTime, LocalDateTime endDateAndTime, User user);

    @Query("SELECT SUM(o.totalAmount) FROM Order o WHERE o.createdAt >= :startDateAndTime AND o.createdAt <= :endDateAndTime")
    BigDecimal findTotalOrderAmountSumForToday(@Param("startDateAndTime") LocalDateTime startDateAndTime, @Param("endDateAndTime") LocalDateTime endDateAndTime);

}
