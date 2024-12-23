package com.example.ecommerce.config;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repositories.CategoryRepository;
import com.example.ecommerce.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class DataSeeder {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            if (productRepository.count() == 0) {
                // First create some categories
                Category electronics = new Category();
                electronics.setName("Electronics");
                categoryRepository.save(electronics);

                Category clothing = new Category();
                clothing.setName("Clothing");
                categoryRepository.save(clothing);

                // Create and save some sample products
                Product laptop = new Product();
                laptop.setName("Laptop");
                laptop.setQuantity(10);
                laptop.setPrice(999.99);
                laptop.setPhoto("laptop.jpg");
                laptop.setCategory(electronics);
                productRepository.save(laptop);

                Product smartphone = new Product();
                smartphone.setName("Smartphone");
                smartphone.setQuantity(20);
                smartphone.setPrice(599.99);
                smartphone.setPhoto("smartphone.jpg");
                smartphone.setCategory(electronics);
                productRepository.save(smartphone);

                Product tShirt = new Product();
                tShirt.setName("T-Shirt");
                tShirt.setQuantity(50);
                tShirt.setPrice(29.99);
                tShirt.setPhoto("tshirt.jpg");
                tShirt.setCategory(clothing);
                productRepository.save(tShirt);

                System.out.println("Database has been seeded!");
            }
        };
    }
}
