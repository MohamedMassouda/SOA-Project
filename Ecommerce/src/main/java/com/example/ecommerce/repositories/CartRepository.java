package com.example.ecommerce.repositories;

import com.example.ecommerce.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.client.id = ?1")
    Optional<Cart> findByClientId(Long clientId);
    @Query("SELECT c FROM Cart c WHERE c.client.user.id = ?1")
    Optional<Cart> findByUserId(Long userId);
}
