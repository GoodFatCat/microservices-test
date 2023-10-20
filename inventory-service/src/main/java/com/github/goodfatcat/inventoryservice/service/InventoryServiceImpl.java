package com.github.goodfatcat.inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.goodfatcat.inventoryservice.dto.InventoryResponse;
import com.github.goodfatcat.inventoryservice.repository.InventoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class InventoryServiceImpl implements InventoryService {

	private InventoryRepository inventoryRepository;

	@Transactional(readOnly = true)
	@Override
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		return inventoryRepository.findBySkuCodeIn(skuCode).stream()
				.map(inventory -> 
					InventoryResponse.builder().skuCode(inventory.getSkuCode())
							.isInStock(inventory.getQuantity() > 0)
							.build()
				).toList();
	}
	
	

}
