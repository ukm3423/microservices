package com.productservice.dto;

import java.math.BigDecimal;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Long id; 
    private String categoryName;
    private String name;
	private Double price;
	private String image;
	private Date createdAt; 
	private Date updatedAt;

}
