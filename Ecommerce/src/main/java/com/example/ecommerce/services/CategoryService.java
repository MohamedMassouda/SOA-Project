package com.example.ecommerce.services;

import java.util.List;

import com.example.ecommerce.dto.category.CreateCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ecommerce.repositories.CategoryRepository;
import com.example.ecommerce.models.Category;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name).orElse(null);
    }

    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    public Category create(CreateCategoryDto createCategoryDto) {
        Category category = new Category();
        category.setName(createCategoryDto.getName());
        category.setDescription(createCategoryDto.getDescription());
        return categoryRepository.save(category);
    }

}
