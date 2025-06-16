package com.shopsphere.admin_service.controller;

import com.shopsphere.admin_service.dto.CategoryDTO;
import com.shopsphere.admin_service.dto.ResponseDTO;
import com.shopsphere.admin_service.service.clients.CategoryFeignClient;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/category", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final CategoryFeignClient categoryFeignClient;

    @PostMapping("/admin/save")
    public ResponseEntity<ResponseDTO> post(@Valid @RequestBody CategoryDTO category) {
        return categoryFeignClient.post(category);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<ResponseDTO> updateDetails(@Valid @RequestBody CategoryDTO category) {
        return categoryFeignClient.updateCategoryDetails(category);
    }

    @DeleteMapping("/admin/{categoryName}/delete")
    public ResponseEntity<ResponseDTO> updateDetails(@Valid @PathVariable String categoryName) {
        return categoryFeignClient.delete(categoryName);
    }
}
