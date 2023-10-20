package com.github.goodfatcat.productservice.service;

import java.util.List;

import com.github.goodfatcat.productservice.dto.ProductRequest;
import com.github.goodfatcat.productservice.dto.ProductResponse;

public interface ProductService {
	ProductResponse createProduct(ProductRequest productRequest);
	List<ProductResponse> getAllProducts();
}
