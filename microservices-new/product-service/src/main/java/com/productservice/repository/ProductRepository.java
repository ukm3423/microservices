package com.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.productservice.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // public Product findByProductName(String productName);

}
