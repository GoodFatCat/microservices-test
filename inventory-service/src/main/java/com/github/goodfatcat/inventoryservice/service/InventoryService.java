package com.github.goodfatcat.inventoryservice.service;

import java.util.List;

import com.github.goodfatcat.inventoryservice.dto.InventoryResponse;

public interface InventoryService {
	List<InventoryResponse> isInStock(List<String> skuCode);
}
