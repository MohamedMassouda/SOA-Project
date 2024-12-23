package com.example.ecommerce.services;

import java.util.Collection;
import java.util.List;

import com.example.ecommerce.dto.product.CreateProductDto;
import com.example.ecommerce.errors.NegativeIntegerException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.dto.product.ProductDto;
import com.example.ecommerce.models.Category;
import com.example.ecommerce.models.Product;
import com.example.ecommerce.repositories.CategoryRepository;
import com.example.ecommerce.repositories.ProductRepository;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<ProductDto> findAll(String search, Long categoryId, Double minPrice, Double maxPrice) {
        List<Product> products = productRepository.findAll();

        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        }

        if (search != null) {
            products = products.stream().filter(product -> product.getName().toLowerCase().contains(search.toLowerCase())).toList();
        }

        if (minPrice != null) {
            products = products.stream().filter(product -> product.getPrice() >= minPrice).toList();
        }

        if (maxPrice != null) {
            products = products.stream().filter(product -> product.getPrice() <= maxPrice).toList();
        }

        return ProductDto.from(products);
    }

    public boolean checkStock(Long id, int quantity) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            return product.getQuantity() >= quantity;
        }
        return false;
    }

    public ProductDto findById(Long id) throws IllegalAccessException {
        Product product = productRepository.findById(id).orElse(null);

        if (product != null) {
            return ProductDto.from(product);
        }

        throw new IllegalAccessException("Product not found");
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public ProductDto create(CreateProductDto productDto) throws NegativeIntegerException {
        logger.info("Creating product with name: {}", productDto.getName());
        Category category = categoryRepository.findByName(productDto.getCategoryName()).orElse(null);

        if (category == null) {
            logger.info("Category not found, creating new category with name: {}", productDto.getCategoryName());
            category = categoryService.save(new Category(productDto.getCategoryName()));
        }

        if (productDto.getPrice() < 0 || productDto.getQuantity() < 0) {
            throw new NegativeIntegerException("Price and quantity must be greater than or equal to 0");
        }

        Product product = new Product();

        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(category);
        product.setPhoto(productDto.getPhoto());
        product.setQuantity(productDto.getQuantity());

        return ProductDto.from(productRepository.save(product));

    }

    public List<ProductDto> createProducts(List<CreateProductDto> productDtos) throws NegativeIntegerException {
        return productDtos.stream().map(productDto -> {
            try {
                return create(productDto);
            } catch (NegativeIntegerException e) {
                e.printStackTrace();
            }
            return null;
        }).toList();
    }

    public List<Product> findByCategory(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

}
