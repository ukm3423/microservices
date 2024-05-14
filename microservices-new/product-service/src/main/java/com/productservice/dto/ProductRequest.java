package com.productservice.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

	@NotNull(message = "categoryId is required")
	private Long categoryId; 

	@NotBlank(message = "Name is required")
	private String name; 

	@NotBlank(message = "Price is required")
	private Double price;

	@NotNull(message = "image is required")
    private MultipartFile image;
	
}
