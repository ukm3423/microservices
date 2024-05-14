package com.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.inventoryservice.models.Inventory;
import com.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepo){
		return args -> {
			Inventory inventory1 = new Inventory(); 
			inventory1.setSkuCode("iphone_13");
			inventory1.setQuantity(50);

			Inventory inventory2 = new Inventory(); 
			inventory2.setSkuCode("realme_narzo");
			inventory2.setQuantity(0);

			inventoryRepo.save(inventory1);
			inventoryRepo.save(inventory2);

		};
	}

}
