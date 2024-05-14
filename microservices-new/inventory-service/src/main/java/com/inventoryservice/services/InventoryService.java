package com.inventoryservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inventoryservice.repository.InventoryRepository;

@Service
public class InventoryService {
    
    @Autowired
    private InventoryRepository inventoryRepo;

    @Transactional(readOnly = true)
    public boolean isInStock(String skuCode){
        System.out.println(inventoryRepo.findBySkuCode(skuCode));
        return inventoryRepo.findBySkuCode(skuCode).isPresent();
    }

}
