package com.shopsphere.admin_service.controller;

import com.shopsphere.admin_service.dto.CategoryDTO;
import com.shopsphere.admin_service.dto.ResponseDTO;
import com.shopsphere.admin_service.service.clients.IProductFeignClient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/category", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AdminCategoryController {

    private final IProductFeignClient productFeignClient;

    @PostMapping("/admin/save")
    public ResponseEntity<ResponseDTO> post(@Valid @RequestBody CategoryDTO category) {
        return productFeignClient.post(category);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<ResponseDTO> updateDetails(@Valid @RequestBody CategoryDTO category) {
        return productFeignClient.updateCategoryDetails(category);
    }

    @PutMapping("/admin/{categoryName}/enable")
    public ResponseEntity<ResponseDTO> enable(
            @NotEmpty(message = "Category name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid category name")
            @PathVariable String categoryName) {
        return productFeignClient.enableCategory(categoryName);
    }

    @PutMapping("/admin/{categoryName}/disable")
    public ResponseEntity<ResponseDTO> updateDetails(
            @NotEmpty(message = "Category name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid category name")
            @PathVariable String categoryName) {
        return productFeignClient.disableCategory(categoryName);
    }
}
