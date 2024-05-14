package com.inventoryservice.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.inventoryservice.services.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private InventoryService inventoryService;
    
    @GetMapping("/test")
    public String test(){
        return "inventory-service started";
    }

    @GetMapping("/skucode")
    public boolean isInStock(@Param("sku-code") String skuCode){
        return inventoryService.isInStock(skuCode);
    }


    @GetMapping("/get-products")
	public Object getProductList(){

        Object userResponse = restTemplate.getForEntity("http://localhost:9090/productapp/api/products/retrieve-all-products", Object.class);

		return userResponse;
	}
}
