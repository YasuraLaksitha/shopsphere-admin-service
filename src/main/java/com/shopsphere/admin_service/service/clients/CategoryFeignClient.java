package com.shopsphere.admin_service.service.clients;

import com.shopsphere.admin_service.dto.CategoryDTO;
import com.shopsphere.admin_service.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "products")
public interface CategoryFeignClient {

    @PostMapping("/api/category/admin/save")
    ResponseEntity<ResponseDTO> post(@RequestBody CategoryDTO category);

    @PutMapping("/api/category/admin/update")
    ResponseEntity<ResponseDTO> updateCategoryDetails(@RequestBody CategoryDTO category);

    @PutMapping("/api/category/admin/{categoryName}/enable")
    ResponseEntity<ResponseDTO> enable(@PathVariable final String categoryName);

    @PutMapping("/api/category/admin/{categoryName}/disable")
    ResponseEntity<ResponseDTO> disable(@PathVariable final String categoryName);
}
