package com.shopsphere.admin_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopsphere.admin_service.constants.ApplicationDefaultConstants;
import com.shopsphere.admin_service.dto.ProductDTO;
import com.shopsphere.admin_service.entity.CategoryEntity;
import com.shopsphere.admin_service.entity.ProductEntity;
import com.shopsphere.admin_service.exceptions.NoModificationRequiredException;
import com.shopsphere.admin_service.exceptions.ResourceAlreadyExistException;
import com.shopsphere.admin_service.exceptions.ResourceAlreadyUnavailableException;
import com.shopsphere.admin_service.exceptions.ResourceNotFoundException;
import com.shopsphere.admin_service.repository.CategoryRepository;
import com.shopsphere.admin_service.repository.ProductRepository;
import com.shopsphere.admin_service.service.IFileService;
import com.shopsphere.admin_service.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ObjectMapper objectMapper;

    private final IFileService fileService;

    @Value("${images.products.url}")
    private String productImageUrl;

    @Override
    public void persistProduct(final ProductDTO productDTO, final String category) {
        final CategoryEntity categoryEntity = categoryRepository.findByCategoryNameIgnoreCase(category).orElseThrow(
                () -> new ResourceNotFoundException("Category", "category name", category)
        );

        productRepository.findByProductNameStartsWithIgnoreCase(productDTO.getProductName()).ifPresent((entity) -> {
            throw new ResourceAlreadyExistException("Product", "product name", entity.getProductName());
        });

        final ProductEntity productEntity = objectMapper.convertValue(productDTO, ProductEntity.class);
        productEntity.setCategoryId(categoryEntity.getCategoryId());

        if (productEntity.getProductSpecialPrice() == null)
            productEntity.setProductSpecialPrice(ApplicationDefaultConstants.PRODUCT_SPECIAL_PRICE);
        if (productEntity.getProductQuantity() == null)
            productEntity.setProductQuantity(ApplicationDefaultConstants.PRODUCT_QUANTITY);
        productEntity.setMinimumThreshHoldCount(ApplicationDefaultConstants.MINIMUM_PRODUCT_THRESHOLD_COUNT);

        if (productEntity.getProductQuantity() <= ApplicationDefaultConstants.MINIMUM_PRODUCT_THRESHOLD_COUNT)
            productEntity.setUnavailable(true);

        productRepository.save(productEntity);
    }

    @Override
    public void updateProduct(final ProductDTO productDTO) {
        final ProductEntity productEntity =
                productRepository.findByProductNameStartsWithIgnoreCase(productDTO.getProductName()).orElseThrow(
                        () -> new ResourceNotFoundException("Product", "product name", productDTO.getProductName())
                );

        if (Objects.equals(productEntity.getProductSpecialPrice(), productDTO.getProductSpecialPrice()) &&
                Objects.equals(productEntity.getProductPrice(), productDTO.getProductPrice()) &&
                Objects.equals(productEntity.getProductDescription(), productDTO.getProductDescription()) &&
                Objects.equals(productEntity.getProductQuantity(), productDTO.getProductQuantity()) &&
                Objects.isNull(productDTO.getProductDiscountPrice()))
            throw new NoModificationRequiredException("Product", "product name", productDTO.getProductName());

        productEntity.setProductPrice(productDTO.getProductPrice());
        productEntity.setProductSpecialPrice(productDTO.getProductSpecialPrice());
        productEntity.setProductQuantity(productDTO.getProductQuantity());
        productEntity.setProductDescription(productDTO.getProductDescription());

        productEntity.setProductSpecialPrice(calculateProductSpecialPrice(
                productDTO.getProductDiscountPrice(),
                productDTO.getProductPrice()
        ));
        if (productEntity.getProductQuantity() <= ApplicationDefaultConstants.MINIMUM_PRODUCT_THRESHOLD_COUNT)
            productEntity.setUnavailable(true);

        productRepository.save(productEntity);
    }

    @Override
    public void updateProductImage(final MultipartFile image, final String productName) throws Exception {
        final ProductEntity productEntity =
                productRepository.findByProductNameStartsWithIgnoreCase(productName).orElseThrow(
                        () -> new ResourceNotFoundException("Product", "product name", productName)
                );


        final String uploadImage = fileService.uploadImage(image, productImageUrl);
        productEntity.setProductImage(createImageUrl(uploadImage));
        productRepository.save(productEntity);
    }

    @Override
    public boolean removeProductByName(final String productName) {
        final ProductEntity productEntity =
                productRepository.findByProductNameStartsWithIgnoreCase(productName).orElseThrow(
                        () -> new ResourceNotFoundException("Product", "product name", productName)
                );
        if (productEntity.isUnavailable())
            throw new ResourceAlreadyUnavailableException("Product", "product name", productName);
        productEntity.setUnavailable(true);

        return productEntity.isUnavailable();
    }

    private double calculateProductSpecialPrice(final Double productDiscountPrice, final Double productPrice) {
        Objects.requireNonNull(productDiscountPrice);
        Objects.requireNonNull(productPrice);

        return productPrice - productDiscountPrice;
    }

    private String createImageUrl(final String string) {
        return productImageUrl.endsWith("/") ? productImageUrl + string : productImageUrl + "/" + string;
    }
}
