package com.kawser.cleanspringbootproject.api.services.impl;

import com.kawser.cleanspringbootproject.api.models.Product;
import com.kawser.cleanspringbootproject.api.models.dto.ProductDTO;
import com.kawser.cleanspringbootproject.api.repositories.ProductRepository;
import com.kawser.cleanspringbootproject.api.services.IProductService;
import com.kawser.cleanspringbootproject.auth.services.IAuthorizationService;
import com.kawser.cleanspringbootproject.exception.api.domain.common.ModelValidationException;
import com.kawser.cleanspringbootproject.exception.api.domain.pagination.InvalidArgumentsToPaginationException;
import com.kawser.cleanspringbootproject.exception.api.domain.pagination.InvalidSortDirectionException;
import com.kawser.cleanspringbootproject.exception.api.domain.product.ProductNotFoundException;
import com.kawser.cleanspringbootproject.exception.api.domain.product.ProductsEmptyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * This class represents the service that is responsible for managing the products.
 * 
 */
@Service
@Primary
@Qualifier("standard")
@Slf4j
public class ProductService implements IProductService {

    private final IAuthorizationService authorizationService;

    @Autowired
    private ProductRepository productRepository;

    ProductService(IAuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }

    /**
     * Retrieve products with pagination.
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     *
     * @param page the page number
     * @param size the number of elements per page
     * @param sort the sorting criteria (property and direction)
     * @throws InvalidArgumentsToPaginationException If the page or size are negative, the exception InvalidArgumentsToPagination is thrown.
     * @throws InvalidSortDirectionException If the sorting direction is invalid (not "asc" or "desc"), the exception InvalidSortDirectionException is thrown.
     * @throws ProductsEmptyException If there are no products in the database, the exception ProductsEmptyException is thrown.
     * @return the products list with pagination
     *
     */
    @Cacheable(value = "products", key = "#page.toString() + #size.toString() + T(java.util.Arrays).toString(#sort)")
    public Iterable<ProductDTO> getWithPagination(int page, int size, String[] sort) {

        if (page < 0 || size < 0) {
            throw new InvalidArgumentsToPaginationException();
        }

        if (sort.length != 2 || (!sort[1].equalsIgnoreCase("asc") && !sort[1].equalsIgnoreCase("desc"))) {
            throw new InvalidSortDirectionException();
        }

        // If the size is greater than 60, set it to 60
        if (size > 60) {
            size = 60;
        }

        log.info("Getting all products with pagination, page {} and size {}", page, size);

        String property = sort[0];
        Sort.Direction direction = Sort.Direction.fromString(sort[1]);

        Pageable pageRequest = PageRequest.of(page, size, Sort.by(direction, property));

        Page<Product> productPage = productRepository.findAll(pageRequest);

        if (!productPage.iterator().hasNext()) {
            throw new ProductsEmptyException();
        }

        return productPage.map(ProductDTO::from);
    }

    /**
     * Create a new product with the given data and save it to the database.
     * CacheEvict annotation is used to remove all entries from the cache when a new product is created.
     * 
     * @param product the data of the new product
     *
     */
    @CacheEvict(value = "products", allEntries = true)
    public void createProduct(ProductDTO product) {
        log.info("Creating product with name {}", product.name());

        if (productRepository.existsByCode(product.code())){
            throw new ModelValidationException("product.code_already_exists", product.code());
        }

        if (productRepository.existsByName(product.name())){
            throw new ModelValidationException("product.name_already_exists", product.name());
        }

        long currentUserId = authorizationService.getCurrentUserId();

        Product newProduct = Product.builder()
                .code(product.code())
                .name(product.name())
                .price(product.price())
                .description(product.description())
                .build();
        newProduct.setEntryBy(currentUserId);
        newProduct.setEntryDate(LocalDateTime.now());

        productRepository.save(newProduct);
    }

    /**
     * Retrieve a specific product by its ID. 
     * Cacheable annotation is used to cache the result of this method, so that the next time it is called with the same parameters, the result is returned from the cache.
     * 
     * @param productId the ID of the product to retrieve
     * @throws ProductNotFoundException If the product does not exist, the exception ProductNotFoundException is thrown.
     * @return the product with the given ID
     */
    @Cacheable(value = "products", key = "#productId")
    public Optional<ProductDTO> getProductById(Long productId) {
        log.info("Getting product by ID {}", productId);

        Optional<Product> product = productRepository.findById(productId);
        
        // Return the product if it exists, otherwise throw an exception
        if (product.isPresent()) {
            return Optional.of(ProductDTO.from(product.get()));
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    /**
     * Update the product with the given ID with the new data. 
     * CacheEvict annotation is used to remove all entries from the cache when a product is updated.
     * 
     * @param productId the ID of the product to update
     * @param updatedProduct the new data of the product
     * @throws ProductNotFoundException If the product does not exist, the exception ProductNotFoundException is thrown.
     * 
     */
    @CacheEvict(value = "products", allEntries = true)
    public void updateProduct(Long productId, ProductDTO updatedProduct) {
        log.info("Updating product with ID {}", productId);

        Optional<Product> existingProduct = productRepository.findById(productId);
        long currentUserId = authorizationService.getCurrentUserId();

        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();

            product.setCode(updatedProduct.code());
            product.setName(updatedProduct.name());
            product.setPrice(updatedProduct.price());
            product.setDescription(updatedProduct.description());

            product.setUpdatedBy(currentUserId);
            product.setUpdatedDate(LocalDateTime.now());

            productRepository.save(product);
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

    /**
     * Delete the product with the given ID.
     * CacheEvict annotation is used to remove all entries from the cache when a product is deleted.
     * 
     * @param productId the ID of the product to delete
     * @throws ProductNotFoundException If the product does not exist, the exception ProductNotFoundException is thrown.
     * 
     */
    @CacheEvict(value = "products", allEntries = true)
    public void deleteProduct(Long productId) {
        log.info("Deleting product with ID {}", productId);

        Optional<Product> existingProduct = productRepository.findById(productId);

        if (existingProduct.isPresent()) {
            productRepository.delete(existingProduct.get());
        } else {
            throw new ProductNotFoundException(productId);
        }
    }

}
