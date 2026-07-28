package com.project.library.service;

import com.project.library.entity.Category;
import com.project.library.exception.ResourceNotFoundException;
import com.project.library.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // GET CATEGORY BY ID
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));
    }

    // UPDATE CATEGORY
    public Category updateCategory(Long categoryId, Category updatedCategory) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        existingCategory.setCategoryName(updatedCategory.getCategoryName());

        return categoryRepository.save(existingCategory);
    }

    // DELETE CATEGORY BY ID
    public void deleteCategory(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(categoryId);
    }
}