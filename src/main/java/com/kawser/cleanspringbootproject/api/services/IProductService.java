package com.kawser.cleanspringbootproject.api.services;

import com.kawser.cleanspringbootproject.api.models.dto.ProductDTO;
import java.util.Optional;

public interface IProductService {

    Iterable<ProductDTO> getWithPagination(int page, int size, String[] sort);

    void createProduct(ProductDTO product);

    Optional<ProductDTO> getProductById(Long productId);

    void updateProduct(Long productId, ProductDTO updatedProduct);

    void deleteProduct(Long productId);

}