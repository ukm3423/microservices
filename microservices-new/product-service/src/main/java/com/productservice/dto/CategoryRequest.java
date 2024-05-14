package com.productservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryRequest {
    
    @NotBlank(message = "Category name is required")
    private String categoryName; 

    @NotBlank(message = "Description is required")
    private String description;
}
