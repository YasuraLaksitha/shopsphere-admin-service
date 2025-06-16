package com.shopsphere.admin_service.service.clients;

import com.shopsphere.admin_service.dto.CategoryDTO;
import com.shopsphere.admin_service.dto.ProductDTO;
import com.shopsphere.admin_service.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "products")
public interface IProductFeignClient {

    @PostMapping("/api/category/admin/save")
    ResponseEntity<ResponseDTO> post(@RequestBody CategoryDTO category);

    @PutMapping("/api/category/admin/update")
    ResponseEntity<ResponseDTO> updateCategoryDetails(@RequestBody CategoryDTO category);

    @PutMapping("/api/category/admin/{categoryName}/enable")
    ResponseEntity<ResponseDTO> enableCategory(@PathVariable final String categoryName);

    @PutMapping("/api/category/admin/{categoryName}/disable")
    ResponseEntity<ResponseDTO> disableCategory(@PathVariable final String categoryName);

    @PostMapping("/api/admin/categories/{category}/save")
    ResponseEntity<ResponseDTO> save(@RequestBody ProductDTO product, @PathVariable final String category);

    @PutMapping("/api/admin/update")
    ResponseEntity<ResponseDTO> updateProductDetails(@RequestBody ProductDTO productDTO);

    @PutMapping(path = "/api/admin/{productName}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<ResponseDTO> updateImage(@PathVariable String productName, @RequestPart("file") MultipartFile image) throws Exception;

    @PutMapping("/api/admin/{productName}/enable")
    ResponseEntity<ResponseDTO> enableProduct(@PathVariable final String productName);

    @PutMapping("/api/admin/{productName}/disable")
    ResponseEntity<ResponseDTO> disableProduct(@PathVariable final String productName);
}
