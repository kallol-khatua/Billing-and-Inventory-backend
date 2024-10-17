package com.backend.billing_and_inventory.repository;

import com.backend.billing_and_inventory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //    Optional<User> findByEmail(String email);
    User findByEmail(String email);
}
