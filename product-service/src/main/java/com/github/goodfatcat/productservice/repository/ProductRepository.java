package com.github.goodfatcat.productservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.goodfatcat.productservice.model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}
