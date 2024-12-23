package com.example.ecommerce.controllers;

import java.util.List;

import com.example.ecommerce.dto.product.CreateProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.services.ProductService;
import com.example.ecommerce.dto.product.ProductDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll(@RequestParam(required = false) String search, @RequestParam(required = false) Long categoryId, @RequestParam(required = false) Double minPrice, @RequestParam(required = false) Double maxPrice) {
        return ResponseEntity.ok(productService.findAll(search, categoryId, minPrice, maxPrice));
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@NonNull @RequestBody CreateProductDto createProductDto) {
        try {

            ProductDto product = productService.create(createProductDto);

            if (product == null) {
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> bulkCreate(@NonNull @RequestBody List<CreateProductDto> createProductDtos) {
        try {
            List<ProductDto> products = productService.createProducts(createProductDtos);

            if (products == null) {
                return ResponseEntity.badRequest().body(null);
            }

            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findById(@PathVariable Long id) {
        ProductDto product = null;
        try {
            product = productService.findById(id);

            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(product);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        ProductDto product = null;
        try {
            product = productService.findById(id);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            productService.deleteById(id);

            return ResponseEntity.ok().build();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
