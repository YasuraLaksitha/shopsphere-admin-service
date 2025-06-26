package com.shopsphere.admin_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(
        name = "Category",
        description = "Schema to hold category information"
)
public class CategoryDTO {

    @Schema(description = "Category name for category object", example = "Sports")
    @NotEmpty(message = "Category name cannot be empty")
    private String categoryName;

    @Schema(description = "Category description for category object", example = "Category for products related to sports")
    private String categoryDescription;
}
