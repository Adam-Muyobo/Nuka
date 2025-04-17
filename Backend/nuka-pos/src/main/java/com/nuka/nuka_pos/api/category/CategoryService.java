package com.nuka.nuka_pos.api.category;

import com.nuka.nuka_pos.api.category.exceptions.CategoryNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));
        return mapToResponse(category);
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Long id, Category updatedData) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + id));

        if (updatedData.getName() != null) existing.setName(updatedData.getName());
        if (updatedData.getDescription() != null) existing.setDescription(updatedData.getDescription());
        if (updatedData.getOrganization() != null) existing.setOrganization(updatedData.getOrganization());

        categoryRepository.save(existing);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    public List<CategoryResponse> getCategoriesByOrganizationId(Long organizationId) {
        return categoryRepository.findByOrganizationId(organizationId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                category.getOrganization().getId()
        );
    }
}
