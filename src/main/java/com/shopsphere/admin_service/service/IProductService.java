package com.shopsphere.admin_service.service;

import com.shopsphere.admin_service.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IProductService {

    /**
     *
     * @param productDTO - productDTO object
     */
    void persistProduct(final ProductDTO productDTO, final String category);


    /**
     *
     * @param productDTO - productDTO object
     */
    void updateProduct(final ProductDTO productDTO);

    /**
     *
     * @param image - image
     * @param productName - name of the product
     */
    void updateProductImage(final MultipartFile image, final String productName) throws Exception;

    /**
     *
     * @param productName - name of the product
     * @return - True if product was deleted successfully
     */
    boolean removeProductByName(final String productName);

}
