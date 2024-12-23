package com.example.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ecommerce.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.category.name LIKE %?1%")
    List<Product> findByCategoryName(String categoryName);
    @Query("SELECT p FROM Product p where p.category.id = ?1")
    List<Product> findByCategoryId(Long categoryId);
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> findByName(String name);
    @Query("SELECT p FROM Product p WHERE p.price >= ?1")
    List<Product> findByMinPrice(Double minPrice);
    @Query("SELECT p FROM Product p WHERE p.price <= ?1")
    List<Product> findByMaxPrice(Double maxPrice);
    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1% AND p.category.id = ?2")
    List<Product> findByNameAndCategoryName(String name, Long categoryId);
    @Query("SELECT p FROM Product p WHERE p.price >= ?1 AND p.price <= ?2")
    List<Product> findByPriceRange(Double minPrice, Double maxPrice);
}

