package com.project.library.service;

import com.project.library.entity.Category;
import com.project.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // ADD NEW CATEGORY
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // GET ALL CATEGORIES
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // GET ALL CATEGORIES BY ID
    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    // DELETE CATEGORY BY ID
    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
