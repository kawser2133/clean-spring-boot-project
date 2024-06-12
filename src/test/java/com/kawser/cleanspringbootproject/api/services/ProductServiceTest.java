package com.kawser.cleanspringbootproject.api.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.kawser.cleanspringbootproject.auth.services.impl.AuthorizationService;
import org.junit.jupiter.api.Test;
import com.kawser.cleanspringbootproject.api.models.Product;
import com.kawser.cleanspringbootproject.api.models.dto.ProductDTO;
import com.kawser.cleanspringbootproject.api.repositories.ProductRepository;
import com.kawser.cleanspringbootproject.api.services.impl.ProductService;
import com.kawser.cleanspringbootproject.exception.api.domain.product.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private AuthorizationService authorizationService;

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct() {
        ProductDTO productDTO = new ProductDTO(
                "P0001",
                "Test Product",
                10.0,
                "Test Product Data");

        // Mocking the behavior of productRepository and authorizationService
        when(productRepository.existsByCode(productDTO.code())).thenReturn(false);
        when(productRepository.existsByName(productDTO.name())).thenReturn(false);
        when(authorizationService.getCurrentUserId()).thenReturn(1L);
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Creating the product
        productService.createProduct(productDTO);

        // Verifying if the methods were called
        verify(productRepository, times(1)).existsByCode(productDTO.code());
        verify(productRepository, times(1)).existsByName(productDTO.name());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void testGetProductWhenProductExists() {
        Long productId = 1L;
        Product product = new Product();

        // Mocking the findById method to return a product
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Getting the product
        productService.getProductById(productId);

        // Verifying if the findById method was called once
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testGetProductWhenProductDoesNotExist() {
        Long productId = 1L;

        // Mocking the findById method to return an empty optional
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Getting the product and expecting an exception
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(productId));

        // Verifying if the findById method was called once
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    public void testUpdateProductWhenProductExists() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO(
                "P0001",
                "Test Product",
                10.0,
                "Test Product Data");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setCode("P0001");
        existingProduct.setName("Old Product");
        existingProduct.setPrice(5.0);
        existingProduct.setDescription("Old Description");

        // Mocking the findById method to return the existing product
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));

        // Updating the product
        productService.updateProduct(productId, productDTO);

        // Verifying if the methods were called
        verify(productRepository, times(1)).save(any(Product.class));
        verify(productRepository, times(1)).findById(productId);

        // Asserting if the product was updated correctly
        assertEquals(productDTO.name(), existingProduct.getName());
        assertEquals(productDTO.price(), existingProduct.getPrice());
        assertEquals(productDTO.description(), existingProduct.getDescription());
    }

    @Test
    public void testUpdateProductWhenProductDoesNotExist() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO(
                "P0001",
                "Test Product",
                10.0,
                "Test Product Data");

        // Mocking the findById method to return an empty optional
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // Updating the product and expecting an exception
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(productId, productDTO));

        // Verifying if the findById method was called once and the save method was never called
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    public void testDeleteProductWhenProductExists() {
        Long productId = 1L;
        Product product = new Product();

        // Mocking the findById method to return a product
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Deleting the product
        productService.deleteProduct(productId);

        // Verifying if the delete method was called once and the findById method was
        // called once
        verify(productRepository, times(1)).delete(product);
        verify(productRepository, times(1)).findById(productId);
    }

}