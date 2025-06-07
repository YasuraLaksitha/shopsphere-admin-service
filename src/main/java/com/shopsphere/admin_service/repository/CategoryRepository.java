package com.shopsphere.admin_service.repository;


import com.shopsphere.admin_service.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long>, JpaSpecificationExecutor<CategoryEntity> {

    Optional<CategoryEntity> findByCategoryNameIgnoreCase(String categoryName);
}
