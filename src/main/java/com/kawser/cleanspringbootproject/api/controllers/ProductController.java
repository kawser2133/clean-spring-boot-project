package com.kawser.cleanspringbootproject.api.controllers;

import com.kawser.cleanspringbootproject.api.models.Product;
import com.kawser.cleanspringbootproject.api.models.dto.ProductDTO;
import com.kawser.cleanspringbootproject.api.services.IProductService;
import com.kawser.cleanspringbootproject.api.services.impl.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The ProductController class is responsible for handling the HTTP requests related to products.
 * It uses the ProductService to perform operations on the database. 
 * 
 * @see ProductService
 * @see Product
 */
@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "403", description = "You are not authorized to access this resource"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
})
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    public ProductController(@Qualifier("standard") IProductService productService) {
        this.productService = productService;
    }

    private final ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.getDefault());

    /**
     * This method returns all products with pagination, sorting the results by the given fields.
     * @param page The page number to be returned.
     * @param size The number of elements per page. Note: the default value is 10 and the maximum value is 60.
     * @param sort An array of strings with the format "field,direction" to sort the results. Note: the default value is "name,asc".
     * @return The list of products found.
     */
    @Operation(summary = "Find all products with pagination",
            description = "Find all products with pagination. Note: Maximum size is 60.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments to pagination"),
            @ApiResponse(responseCode = "404", description = "Products not found")
    })
    @GetMapping("/paginated")
    public ResponseEntity<Iterable<ProductDTO>> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name,asc") String[] sort) {
        Iterable<ProductDTO> entities = productService.getWithPagination(page, size, sort);
        return ResponseEntity.ok(entities);
    }

    /**
     * This method creates a new product with the given data.
     * @param productDTO The DTO of the product to be created, passed as a request body.
     * @return A message indicating that the product was successfully created.
     */
    @Operation(summary = "Create a new product", description = "Create a new product with the given data")
    @ApiResponse(responseCode = "200", description = "Product created")
    @PostMapping("/create")
    public ResponseEntity<String> save(
            @RequestBody @Valid ProductDTO productDTO) {
        
        productService.createProduct(productDTO);
        return ResponseEntity.ok(bundle.getString("product.successfully_created"));
    }

    /**
     * This method returns a specific product by its ID. 
     * @param productId The ID of the product to be found, passed as a request parameter.
     * @return The DTO of the product found.
     */
    @Operation(summary = "Find a product by its ID", description = "Find a product by its ID")
    @ApiResponse(responseCode = "200", description = "Product found")
    @GetMapping("/find")
    public ResponseEntity<Optional<ProductDTO>> findById(
            @RequestParam(name = "product") Long productId) {
        
        Optional<ProductDTO> product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    /**
     * This method updates a product with the given data.
     * @param productId The ID of the product to be updated, passed as a request parameter.
     * @param productUpdated The DTO of the product with the new data, passed as a request body.
     * @return A message indicating that the product was successfully updated.
     */
    @Operation(summary = "Update a product", description = "Update a product with the given data")
    @ApiResponse(responseCode = "200", description = "Product updated")
    @PutMapping("/update")
    public ResponseEntity<String> update(
            @RequestParam(name = "product") Long productId,
            @RequestBody @Valid ProductDTO productUpdated) {
        
        productService.updateProduct(productId, productUpdated);
        return ResponseEntity.ok(bundle.getString("product.successfully_updated"));
    }

    /**
     * This method deletes a product by its ID.
     * @param productId The ID of the product to be deleted, passed as a request parameter.
     * @return A message indicating that the product was successfully deleted.
     */
    @Operation(summary = "Delete a product", description = "Delete a product by its ID")
    @ApiResponse(responseCode = "200", description = "Product deleted")
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(
            @RequestParam(name = "product") Long productId) {

        productService.deleteProduct(productId);
        return ResponseEntity.ok(bundle.getString("product.successfully_deleted"));
    }
}
