package com.github.goodfatcat.productservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.goodfatcat.productservice.dto.ProductRequest;
import com.github.goodfatcat.productservice.dto.ProductResponse;
import com.github.goodfatcat.productservice.model.Product;
import com.github.goodfatcat.productservice.repository.ProductRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepo;

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
	Product product = Product.builder().name(productRequest.getName())
		.descrioption(productRequest.getDescription()).price(productRequest.getPrice()).build();

	Product savedProduct = productRepo.save(product);

	log.info("Product {} is saved", savedProduct.getId());

	ProductResponse productResponse = ProductResponse.builder().id(savedProduct.getId())
		.name(savedProduct.getName()).descrioption(savedProduct.getDescrioption())
		.price(savedProduct.getPrice()).build();

	return productResponse;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
	List<Product> products = productRepo.findAll();
	List<ProductResponse> productResponseList = products.stream()
		.map(product -> ProductResponse.builder().id(product.getId()).name(product.getName())
			.descrioption(product.getDescrioption()).price(product.getPrice()).build())
		.toList();
	return productResponseList;
    }

}
