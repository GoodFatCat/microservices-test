package com.github.goodfatcat.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.goodfatcat.orderservice.dto.OrderRequest;
import com.github.goodfatcat.orderservice.dto.OrderResponse;
import com.github.goodfatcat.orderservice.model.Order;
import com.github.goodfatcat.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
	private OrderService orderService;

	@PostMapping
	@CircuitBreaker(name = "inventory")
	public ResponseEntity<OrderResponse> placeOrder(
			@RequestBody OrderRequest orderRequest) {
		Order placedOrder = orderService.placeOrder(orderRequest);

		OrderResponse orderResponse = getResponseEntity(placedOrder);

		return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
	}

	private OrderResponse getResponseEntity(Order placedOrder) {
		OrderResponse orderResponse = new OrderResponse();
		orderResponse.setId(placedOrder.getId());
		orderResponse.setOrderLineItems(placedOrder.getOrderLineItems());
		orderResponse.setOrderNumber(placedOrder.getOrderNumber());
		return orderResponse;
	}
}
