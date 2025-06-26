package com.shopsphere.admin_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "Product",
        description = "Schema to hold product information"
)
public class ProductDTO {

    @Schema(description = "Product name for product object", example = "Tennis ball")
    @NotEmpty(message = "Product name is required")
    @Length(min = 3, message = "Minimum character length for product name should be 3")
    private String productName;

    @Schema(description = "Product description for product object", example = "3 ounces")
    private String productDescription;

    @Schema(description = "Product quantity for product object", example = "100")
    @Positive(message = "Product quantity should be positive")
    private Integer productQuantity;

    @Schema(description = "Product image for product object")
    private String productImage;

    @Schema(description = "Product price for product object is USD", example = "2.00")
    @NotNull(message = "Product price is required")
    private Double productPrice;

    @Schema(description = "Product discount for product object is USD", example = "0.5")
    @PositiveOrZero(message = "Product quantity should be zero or positive")
    private Double productDiscountPrice;

    @Schema(description = "Product special price for product object is USD", example = "1.5")
    @PositiveOrZero(message = "Product special price should be zero or positive")
    private Double productSpecialPrice;
}
