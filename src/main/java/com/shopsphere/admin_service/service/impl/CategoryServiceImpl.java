package com.shopsphere.admin_service.service.impl;

import ch.qos.logback.core.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsphere.admin_service.dto.CategoryDTO;
import com.shopsphere.admin_service.entity.CategoryEntity;
import com.shopsphere.admin_service.exceptions.NoModificationRequiredException;
import com.shopsphere.admin_service.exceptions.ResourceAlreadyExistException;
import com.shopsphere.admin_service.exceptions.ResourceAlreadyUnavailableException;
import com.shopsphere.admin_service.exceptions.ResourceNotFoundException;
import com.shopsphere.admin_service.repository.CategoryRepository;
import com.shopsphere.admin_service.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final CategoryRepository categoryRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void persistCategory(final CategoryDTO category) {
        categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName()).ifPresent((existingCategory) -> {
            throw new ResourceAlreadyExistException("Category", "Category name", existingCategory.getCategoryName());
        });

        if (StringUtil.isNullOrEmpty(category.getCategoryDescription()))
            category.setCategoryDescription(category.getCategoryName());

        categoryRepository.save(objectMapper.convertValue(category, CategoryEntity.class));
    }

    @Override
    public void updateCategoryByName(final CategoryDTO category) {
        final CategoryEntity categoryEntity =
                categoryRepository.findByCategoryNameIgnoreCase(category.getCategoryName())
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Category", "category name", category.getCategoryName())
                        );
        if (categoryEntity.getCategoryDescription().equals(category.getCategoryDescription())) {
            throw new NoModificationRequiredException("Category", "category name", category.getCategoryName());
        }
        categoryEntity.setCategoryDescription(category.getCategoryDescription());
        categoryRepository.save(categoryEntity);
    }

    @Override
    public boolean deleteCategoryByName(final String categoryName) {

        final CategoryEntity categoryEntity =
                categoryRepository.findByCategoryNameIgnoreCase(categoryName).orElseThrow(
                        () -> new ResourceNotFoundException("Category", "category name", categoryName)
                );

        if (categoryEntity.isUnavailable())
            throw new ResourceAlreadyUnavailableException("Category", "category name", categoryName);

        categoryEntity.setUnavailable(true);
        categoryRepository.save(categoryEntity);

        return true;
    }
}
