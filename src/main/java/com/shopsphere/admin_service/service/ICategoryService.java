package com.shopsphere.admin_service.service;

import com.shopsphere.admin_service.dto.CategoryDTO;
import jakarta.validation.Valid;

public interface ICategoryService {

    /**
     *
     * @param category - categoryDTO object
     */
    void persistCategory(CategoryDTO category);


    /**
     *
     * @param category - category
     */
    void updateCategoryByName(@Valid CategoryDTO category);

    /**
     *
     * @param categoryName - name of category
     */
    boolean deleteCategoryByName(@Valid String categoryName);
}
