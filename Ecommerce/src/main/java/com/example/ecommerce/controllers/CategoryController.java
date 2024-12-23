package com.example.ecommerce.controllers;

import java.util.List;

import com.example.ecommerce.dto.category.CreateCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import com.example.ecommerce.models.Category;
import com.example.ecommerce.services.CategoryService;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/name/{name}")
    public Category findByName(@PathVariable String name) {
        return categoryService.findByName(name);
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(@NonNull @RequestBody CreateCategoryDto createCategoryDto) {
        Category category = categoryService.create(createCategoryDto);
        return ResponseEntity.ok(category);
    }

}
