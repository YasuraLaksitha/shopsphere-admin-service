package com.shopsphere.admin_service.controller;

import com.shopsphere.admin_service.dto.CategoryDTO;
import com.shopsphere.admin_service.dto.ErrorResponseDTO;
import com.shopsphere.admin_service.dto.ResponseDTO;
import com.shopsphere.admin_service.service.clients.IProductFeignClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "Admin category controller",
        description = "Admin relation operations for category service"
)

@RestController
@RequestMapping(value = "/api/category", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
@Validated
public class AdminCategoryController {

    private final IProductFeignClient productFeignClient;

    @Operation(
            summary = "Create category",
            description = "REST API to create a new category"
    )
    @ApiResponse(
            responseCode = "201",
            description = "HTTP Status Code CREATED"
    )
    @ApiResponse(
            responseCode = "409",
            description = "HTTP Status Code CONFLICT",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @PostMapping("/admin/save")
    public ResponseEntity<ResponseDTO> post(@Valid @RequestBody CategoryDTO category) {
        return productFeignClient.post(category);
    }

    @Operation(
            summary = "Update category",
            description = "REST API to update category details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Code OK"
    )
    @ApiResponse(
            responseCode = "404",
            description = "HTTP Status Code NOT_FOUND",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "HTTP Status Code BAD_REQUEST",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @PutMapping("/admin/update")
    public ResponseEntity<ResponseDTO> updateDetails(@Valid @RequestBody CategoryDTO category) {
        return productFeignClient.updateCategoryDetails(category);
    }

    @Operation(
            summary = "enable category",
            description = "REST API to enable disabled category"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Code OK"
    )
    @ApiResponse(
            responseCode = "409",
            description = "HTTP Status Code CONFLICT",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "HTTP Status Code BAD_REQUEST",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @PutMapping("/admin/{categoryName}/enable")
    public ResponseEntity<ResponseDTO> enable(
            @NotEmpty(message = "Category name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid category name")
            @PathVariable String categoryName) {
        return productFeignClient.enableCategory(categoryName);
    }

    @Operation(
            summary = "disable category",
            description = "REST API to disable enabled category details"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Code OK"
    )
    @ApiResponse(
            responseCode = "403",
            description = "HTTP Status Code FORBIDDEN",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "HTTP Status Code BAD_REQUEST",
            content = @Content(
                    schema = @Schema(implementation = ErrorResponseDTO.class)
            )
    )
    @PutMapping("/admin/{categoryName}/disable")
    public ResponseEntity<ResponseDTO> disable(
            @NotEmpty(message = "Category name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid category name")
            @PathVariable String categoryName) {
        return productFeignClient.disableCategory(categoryName);
    }
}
