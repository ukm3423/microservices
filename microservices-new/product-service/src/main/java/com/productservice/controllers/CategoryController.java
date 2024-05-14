package com.productservice.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.productservice.dto.CategoryRequest;
import com.productservice.models.Category;
import com.productservice.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @CrossOrigin
    @PostMapping("/add")
    public ResponseEntity<Map<Object, Object>> addCategory(@RequestBody CategoryRequest category) {

        Category checkExisting = categoryService.searchByCategoryName(category.getCategoryName());
        if(checkExisting != null) throw new IllegalStateException("Category Name already exists !!");

        Map<Object, Object> resp = new HashMap<>();

        Category c1 = categoryService.addCategory(category);

        resp.put("data", c1);
        resp.put("message", "Category Added Successfully");
        resp.put("status", true);

        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @CrossOrigin
    @GetMapping("/get-category-list")
    public ResponseEntity<Map<Object, Object>> getCategoryList(
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit) {

        Pageable pageable = PageRequest.of(offset, limit);
        Page<Category> categoryPage = categoryService.getCategories(pageable);

        List<Category> categoryList = categoryPage.getContent();

        Map<Object, Object> resp = new HashMap<>();
        resp.put("message", "Retrieve All Categories");
        resp.put("data", categoryList);
        resp.put("currentPage", categoryPage.getNumber());
        resp.put("totalItems", categoryPage.getTotalElements());
        resp.put("totalPages", categoryPage.getTotalPages());
        resp.put("status", true);

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @CrossOrigin
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<Object, Object>> deleteCategory(@PathVariable Long id) {

        Boolean isdeleted = categoryService.deleteCategoryById(id);
        Map<Object, Object> resp = new HashMap<Object, Object>();

        if (isdeleted) {
            resp.put("message", "Deleted Successfully");
            resp.put("data", id);
            resp.put("status", true);
        } else {
            resp.put("status", false);
            resp.put("message", "Something went wrong !!");
            resp.put("data", id);
        }

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

    @CrossOrigin
    @GetMapping("/get-category/{id}")
    public ResponseEntity<Map<Object, Object>> getCategoryById(@PathVariable("id") Long categoryId) {

        Category category = categoryService.getCategoryById(categoryId);

        if (category == null)
            throw new IllegalStateException("Cateogry Not Found of Id : " + categoryId);

        Map<Object, Object> res = new HashMap<Object, Object>();
        res.put("message", "Category Retrieved Successfully");
        res.put("data", category);
        res.put("status", true);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @CrossOrigin
    @PutMapping("/update-category/{id}")
    public ResponseEntity<Map<Object, Object>> updateCategory(@PathVariable("id") Long categoryId,
            @Valid @RequestBody CategoryRequest req ,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Construct error response
            Map<Object, Object> errorResponse = new HashMap<>();
            errorResponse.put("timestamp", LocalDateTime.now());
            errorResponse.put("status", HttpStatus.BAD_REQUEST.value());
            errorResponse.put("error", "Validation Error");
            errorResponse.put("message",
                    "Validation failed for object 'categoryRequest'. Error count: " + bindingResult.getErrorCount());

            List<Map<String, String>> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                Map<String, String> error = new HashMap<>();
                error.put("field", fieldError.getField());
                error.put("message", fieldError.getDefaultMessage());
                errors.add(error);
            }
            errorResponse.put("errors", errors);

            return ResponseEntity.badRequest().body(errorResponse);
        }

        Category checkExisting = categoryService.searchByCategoryName(req.getCategoryName());
        if (checkExisting != null && !checkExisting.getId().equals(categoryId)) {
            throw new IllegalStateException("Category Name already exists !");
        }
        Category category = categoryService.updateCategoryById(categoryId, req);
        if (category == null)
            throw new IllegalStateException("Cateogry Not Found of Id : " + categoryId);

        Map<Object, Object> res = new HashMap<Object, Object>();
        res.put("message", "Category Updated Successfully");
        res.put("data", category);
        res.put("status", true);

        return ResponseEntity.status(HttpStatus.OK).body(res);
    }


    @CrossOrigin
    @GetMapping("/get-list")
    public ResponseEntity<Map<Object, Object>> getList() {

        List<Category> categoryList = categoryService.getCategoryList();

        Map<Object, Object> resp = new HashMap<>();
        resp.put("message", "Retrieve All Categories");
        resp.put("data", categoryList);
        resp.put("status", true);

        return ResponseEntity.status(HttpStatus.OK).body(resp);
    }

}
