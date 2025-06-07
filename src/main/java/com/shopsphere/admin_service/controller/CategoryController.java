package com.shopsphere.admin_service.controller;

import com.shopsphere.admin_service.constants.ApplicationDefaultConstants;
import com.shopsphere.admin_service.dto.CategoryDTO;
import com.shopsphere.admin_service.dto.ResponseDTO;
import com.shopsphere.admin_service.service.ICategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/api/category", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class CategoryController {

    private final ICategoryService categoryService;

    @PostMapping("/admin/save")
    public ResponseEntity<ResponseDTO> post(@Valid @RequestBody CategoryDTO category) {
        categoryService.persistCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.CREATED)
                        .timestamp(LocalDateTime.now())
                        .message(ApplicationDefaultConstants.RESPONSE_MESSAGE_201)
                        .build());
    }


    @PutMapping("/admin/update")
    public ResponseEntity<ResponseDTO> update(@Valid @RequestBody CategoryDTO category) {
        categoryService.updateCategoryByName(category);
        return ResponseEntity.ok().body(ResponseDTO.builder()
                .status(HttpStatus.OK)
                .timestamp(LocalDateTime.now())
                .message(ApplicationDefaultConstants.RESPONSE_MESSAGE_200)
                .build());
    }

    @DeleteMapping("/admin/delete/{name}")
    public ResponseEntity<ResponseDTO> delete(
            @Pattern(regexp = "[a-zA-Z]+", message = "Invalid category name")
            @PathVariable final String name
    ) {
        return categoryService.deleteCategoryByName(name) ?
                ResponseEntity.ok().body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .message(ApplicationDefaultConstants.RESPONSE_MESSAGE_200)
                        .build()) :

                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(ResponseDTO.builder()
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .timestamp(LocalDateTime.now())
                        .message(ApplicationDefaultConstants.RESPONSE_MESSAGE_417)
                        .build());
    }
}
