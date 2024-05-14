package com.productservice.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.productservice.dto.CategoryRequest;
import com.productservice.models.Category;
import com.productservice.repository.CategoryRepository;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository catRepo;

    public Category addCategory(CategoryRequest req) {

        Category category = Category.builder()
                            .categoryName(req.getCategoryName())
                            .description(req.getDescription())
                            .createdAt(new Date())
                            .updatedAt(new Date())
                            .build();

        Category resp = catRepo.save(category);

        return resp;
    }

    public List<Category> getCategoryList() {
        return catRepo.findAll();
    }

    public boolean deleteCategoryById(Long id) {
        catRepo.deleteById(id);
        return true;
    }

    public Page<Category> getCategories(Pageable pageable) {
        return catRepo.findAll(pageable);
    }

    public Category getCategoryById(Long id) {
        return catRepo.findById(id).get();
    }

    public Category updateCategoryById(Long categoryId, CategoryRequest req) {

        Optional<Category> op = catRepo.findById(categoryId);
        if (op.isPresent()) {
            Category cat = op.get();
            cat.setCategoryName(req.getCategoryName());
            cat.setDescription(req.getDescription());
            cat.setUpdatedAt(new Date());

            catRepo.save(cat);
            return cat;
        }
        return null;

    }

    public Category searchByCategoryName(String categoryName){

        Category category = catRepo.findByCategoryNameIgnoreCase(categoryName);
        
        return category;
    }

}
