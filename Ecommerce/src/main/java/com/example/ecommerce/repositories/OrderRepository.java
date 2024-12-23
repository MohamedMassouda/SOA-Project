package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.client.id = ?1")
    List<Order> findByClientId(Long clientId);
    @Query("SELECT o FROM Order o WHERE o.client.id = ?1 AND o.id = ?2")
    Optional<Order> findByClientIdAndOrderId(Long clientId, Long orderId);
}
