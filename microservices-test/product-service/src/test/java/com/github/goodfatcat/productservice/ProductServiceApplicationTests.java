package com.github.goodfatcat.productservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.goodfatcat.productservice.dto.ProductRequest;
import com.github.goodfatcat.productservice.dto.ProductResponse;
import com.github.goodfatcat.productservice.model.Product;
import com.github.goodfatcat.productservice.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Slf4j
class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ProductRepository productRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
	dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }
    
    @BeforeEach
    void prepareDB() {
	productRepository.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldCreateProduct() throws Exception {
	ProductRequest productRequest = getProductRequest();

	ProductResponse expectedProductResponse = getExpectedProductResponse();

	MvcResult mvcResult = mockMvc
		.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(productRequest)))
		.andExpect(status().isCreated()).andReturn();

	ProductResponse actualResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
		ProductResponse.class);
	
	log.debug(actualResponse.toString());

	assertEquals(1, productRepository.findAll().size());
	assertEquals(expectedProductResponse.getName(), actualResponse.getName());
	assertEquals(expectedProductResponse.getDescrioption(), actualResponse.getDescrioption());
	assertEquals(expectedProductResponse.getPrice(), actualResponse.getPrice());
    }

    private ProductResponse getExpectedProductResponse() throws JsonProcessingException {
	return ProductResponse.builder().name("Iphone 13").descrioption("Iphone 13").price(BigDecimal.valueOf(1200))
		.build();
    }

    private ProductRequest getProductRequest() {
	ProductRequest productRequest = ProductRequest.builder().name("Iphone 13").description("Iphone 13")
		.price(BigDecimal.valueOf(1200)).build();
	return productRequest;
    }

    @Test
    void shouldGetAllProducts() {
	initData();
	List<Product> actual = productRepository.findAll();
	log.debug(actual.toString());
	assertEquals(3, actual.size());
    }

    private void initData() {
	List<Product> products = new ArrayList<>();

	Product product = Product.builder().name("Iphone 1").descrioption("Iphone 1").price(BigDecimal.valueOf(500))
		.build();
	products.add(product);

	product = Product.builder().name("Iphone 2").descrioption("Iphone 2").price(BigDecimal.valueOf(600)).build();
	products.add(product);

	product = Product.builder().name("Iphone 3").descrioption("Iphone 3").price(BigDecimal.valueOf(700)).build();
	products.add(product);

	productRepository.saveAll(products);
    }

}
