package com.inventoryservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventoryservice.models.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long>{
    
    public Optional<Inventory> findBySkuCode(String skuCode);
}
