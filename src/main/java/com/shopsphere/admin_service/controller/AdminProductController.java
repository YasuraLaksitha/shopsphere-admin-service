package com.shopsphere.admin_service.controller;

import com.shopsphere.admin_service.dto.ErrorResponseDTO;
import com.shopsphere.admin_service.dto.ProductDTO;
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
import org.springframework.web.multipart.MultipartFile;

@Tag(
        name = "Admin products controller",
        description = "Admin relation operations for product service"
)

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/products", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AdminProductController {

    private final IProductFeignClient productFeignClient;

    @Operation(
            summary = "Create Product",
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
    @PostMapping("/admin/categories/{category}/save")
    public ResponseEntity<ResponseDTO> saveProduct(
            @Valid @RequestBody ProductDTO product,
            @NotEmpty(message = "Category name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid category name")
            @PathVariable final String category) {
        return productFeignClient.save(product, category);
    }

    @Operation(
            summary = "Update product",
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
    public ResponseEntity<ResponseDTO> updateProduct(@Valid @RequestBody ProductDTO product) {
        return productFeignClient.updateProductDetails(product);
    }

    @Operation(
            summary = "Update product image",
            description = "REST API to update product image"
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
    @PutMapping("/admin/{productName}/image")
    public ResponseEntity<ResponseDTO> updateProductImage(
            @NotEmpty(message = "Product name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid product name")
            @PathVariable String productName,
            @RequestParam MultipartFile image) throws Exception {
        return productFeignClient.updateImage(productName, image);
    }

    @Operation(
            summary = "enable product",
            description = "REST API to enable disabled product"
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
    @PutMapping("/admin/{productName}/enable")
    public ResponseEntity<ResponseDTO> enableProduct(
            @NotEmpty(message = "Product name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid product name")
            @PathVariable String productName) {
        return productFeignClient.enableProduct(productName);
    }

    @Operation(
            summary = "disable product",
            description = "REST API to disable enabled product"
    )
    @ApiResponse(
            responseCode = "200",
            description = "HTTP Status Code OK"
    )
    @ApiResponse(
            responseCode = "410",
            description = "HTTP Status Code GONE",
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
    @PutMapping("/admin/{productName}/disable")
    ResponseEntity<ResponseDTO> disableProduct(
            @NotEmpty(message = "Product name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid product name")
            @PathVariable final String productName) {
        return productFeignClient.disableProduct(productName);
    }
}
