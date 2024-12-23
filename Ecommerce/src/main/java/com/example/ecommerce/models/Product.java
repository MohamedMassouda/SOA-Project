package com.example.ecommerce.models;

import com.example.ecommerce.dto.product.ProductDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int quantity;
    private double price;
    private String photo;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public boolean isValid() {
        return name != null && !name.isEmpty() && quantity > 0 && price > 0 && photo != null;
    }

    public static Product from(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        product.setPhoto(productDto.getPhoto());
        product.setCategory(category);
        return product;
    }

}
