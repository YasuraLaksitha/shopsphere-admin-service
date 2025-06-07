package com.shopsphere.admin_service.controller;

import com.shopsphere.admin_service.constants.ApplicationDefaultConstants;
import com.shopsphere.admin_service.dto.ProductDTO;
import com.shopsphere.admin_service.dto.ResponseDTO;
import com.shopsphere.admin_service.service.IProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/products", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class ProductController {

    private final IProductService productService;

    @PostMapping("/admin/categories/{category}/save")
    public ResponseEntity<ResponseDTO> save(
            @Valid @RequestBody ProductDTO product,
            @NotEmpty(message = "category name is required") @PathVariable final String category) {
        productService.persistProduct(product, category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.CREATED)
                        .timestamp(LocalDateTime.now())
                        .message(ApplicationDefaultConstants.RESPONSE_MESSAGE_201)
                        .build());
    }


    @PutMapping("/admin/update")
    public ResponseEntity<ResponseDTO> updateProductDetails(@Valid @RequestBody ProductDTO productDTO) {
        productService.updateProduct(productDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .message(ApplicationDefaultConstants.RESPONSE_MESSAGE_200)
                        .build());
    }

    @PutMapping("/admin/{productName}/image")
    public ResponseEntity<ResponseDTO> updateImage(
            @NotEmpty(message = "Product name is required") @PathVariable String productName,
            @RequestParam MultipartFile image) throws Exception {

        productService.updateProductImage(image, productName);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDTO.builder()
                        .status(HttpStatus.OK)
                        .timestamp(LocalDateTime.now())
                        .message(ApplicationDefaultConstants.RESPONSE_MESSAGE_200)
                        .build());
    }

    @DeleteMapping("/admin/{productName}")
    public ResponseEntity<ResponseDTO> removeProduct(
            @NotEmpty(message = "Product name is required") @PathVariable final String productName) {

        return productService.removeProductByName(productName) ?
                ResponseEntity.status(HttpStatus.OK)
                        .body(ResponseDTO.builder()
                                .status(HttpStatus.OK)
                                .timestamp(LocalDateTime.now())
                                .message(ApplicationDefaultConstants.RESPONSE_MESSAGE_200)
                                .build()) :

                ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body(ResponseDTO.builder()
                                .status(HttpStatus.EXPECTATION_FAILED)
                                .timestamp(LocalDateTime.now())
                                .message(ApplicationDefaultConstants.RESPONSE_MESSAGE_417)
                                .build());
    }
}
