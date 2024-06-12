package com.kawser.cleanspringbootproject.api.repositories;

import com.kawser.cleanspringbootproject.api.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByCode(String code);

    boolean existsByName(String name);

}   
