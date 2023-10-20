package com.github.goodfatcat.productservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.goodfatcat.productservice.dto.ProductRequest;
import com.github.goodfatcat.productservice.dto.ProductResponse;
import com.github.goodfatcat.productservice.service.ProductService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {
	private ProductService productService;
	@PostMapping
	public ResponseEntity<ProductResponse> createProduct(
			@RequestBody ProductRequest product) {
		ProductResponse createdProduct = productService.createProduct(product);
		
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductResponse>> getAllProducts() {
		List<ProductResponse> allProducts = productService.getAllProducts();
		return new ResponseEntity<>(allProducts, HttpStatus.OK);
	}
}