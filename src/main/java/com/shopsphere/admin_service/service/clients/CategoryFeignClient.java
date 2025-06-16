package com.shopsphere.admin_service.service.clients;

import com.shopsphere.admin_service.dto.CategoryDTO;
import com.shopsphere.admin_service.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "products")
public interface CategoryFeignClient {

    @PostMapping("/api/category/admin/save")
    ResponseEntity<ResponseDTO> post(@RequestBody CategoryDTO category);

    @PutMapping("/api/category/admin/update")
    ResponseEntity<ResponseDTO> updateCategoryDetails(@RequestBody CategoryDTO category);

    @DeleteMapping("/api/category/admin/{categoryName}/delete")
    ResponseEntity<ResponseDTO> delete(@PathVariable final String name);
}
