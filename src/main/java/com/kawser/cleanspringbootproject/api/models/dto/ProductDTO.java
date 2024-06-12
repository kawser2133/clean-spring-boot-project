package com.kawser.cleanspringbootproject.api.models.dto;

import com.kawser.cleanspringbootproject.api.models.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record ProductDTO(
        @NotBlank(message = "Product code cannot be blank")
        @Size(min = 4, max = 8, message = "Code must be between 4 and 8 characters long")
        String code,

        @NotBlank(message = "Product name cannot be blank")
        @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters long")
        String name,

        @Min(value = 0, message = "Price cannot be less than zero")
        Double price,

        @Size(max = 500, message = "Description must be 500 characters or less")
        String description
) {

    public static ProductDTO from(Product product) {
        return new ProductDTO(
                product.getCode(),
                product.getName(),
                product.getPrice(),
                product.getDescription());
    }

    public static Set<ProductDTO> from(Set<Product> products) {
        return products.stream().map(ProductDTO::from).collect(java.util.stream.Collectors.toSet());
    }
}