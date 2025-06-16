package com.shopsphere.admin_service.controller;

import com.shopsphere.admin_service.dto.ProductDTO;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/products", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AdminProductController {

    private final IProductFeignClient productFeignClient;

    @PostMapping("/admin/categories/{category}/save")
    public ResponseEntity<ResponseDTO> saveProduct(
            @Valid @RequestBody ProductDTO product,
            @NotEmpty(message = "Category name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid category name")
            @PathVariable final String category) {
        return productFeignClient.save(product, category);
    }

    @PutMapping("/admin/update")
    public ResponseEntity<ResponseDTO> updateProduct(@Valid @RequestBody ProductDTO product) {
        return productFeignClient.updateProductDetails(product);
    }

    @PutMapping("/admin/{productName}/image")
    public ResponseEntity<ResponseDTO> updateProductImage(
            @NotEmpty(message = "Product name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid product name")
            @PathVariable String productName,
            @RequestParam MultipartFile image) throws Exception {
        return productFeignClient.updateImage(productName, image);
    }

    @PutMapping("/admin/{productName}/enable")
    public ResponseEntity<ResponseDTO> enableProduct(
            @NotEmpty(message = "Product name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid product name")
            @PathVariable String productName){
        return productFeignClient.enableProduct(productName);
    }

    @PutMapping("/admin/{productName}/disable")
    ResponseEntity<ResponseDTO> disableProduct(
            @NotEmpty(message = "Product name is required")
            @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)*$", message = "Invalid product name")
            @PathVariable final String productName){
        return productFeignClient.disableProduct(productName);
    }
}
