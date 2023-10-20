package com.github.goodfatcat.inventoryservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.goodfatcat.inventoryservice.dto.InventoryResponse;
import com.github.goodfatcat.inventoryservice.service.InventoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/inventory")
@AllArgsConstructor
public class InventoryController {

	private InventoryService inventoryService;

	@GetMapping
	public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam List<String> skuCode) {
		return ResponseEntity.ok(inventoryService.isInStock(skuCode));
	}
}
