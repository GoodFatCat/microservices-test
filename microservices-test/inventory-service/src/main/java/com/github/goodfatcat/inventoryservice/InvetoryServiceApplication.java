package com.github.goodfatcat.inventoryservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.goodfatcat.inventoryservice.model.Inventory;
import com.github.goodfatcat.inventoryservice.repository.InventoryRepository;

@SpringBootApplication
public class InvetoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InvetoryServiceApplication.class, args);
	}

    @Bean
    CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args ->  {
			Inventory inventory = new Inventory();
			inventory.setQuantity(100);
			inventory.setSkuCode("iphone_13");
			inventoryRepository.save(inventory);
			
			inventory = new Inventory();
			inventory.setQuantity(0);
			inventory.setSkuCode("iphone_13_red");
			inventoryRepository.save(inventory);
		};
	}

}