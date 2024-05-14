package com.productservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.productservice.models.Category;

public interface CategoryRepository extends JpaRepository<Category , Long>{
    
    public Category findByCategoryNameIgnoreCase(String categoryName);

}
