package com.kawser.cleanspringbootproject.api.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Each product has an id, a code, a name, a price, and description.
 * 
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Products")
@Builder
public class Product extends Base<Long> {

    /*
     * The code of the product. It cannot be blank.
     */
    @NotBlank(message = "Product code cannot be blank")
    @Size(min = 4, max = 8, message = "Code must be between 4 and 8 characters long")
    private String code;

    /*
     * The name of the product. It cannot be blank.
     */
    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters long")
    private String name;

    /*
     * The price of the product. It cannot be less than zero.
     */
    @Min(value = 0, message = "Price cannot be less than zero")
    private Double price;

    /*
     * The description of the product. It cannot be blank.
     */
    @Size(max = 500, message = "Description must be 500 characters or less")
    private String description;

}
