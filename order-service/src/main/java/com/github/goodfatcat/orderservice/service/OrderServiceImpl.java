package com.github.goodfatcat.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.goodfatcat.orderservice.dto.InventoryResponse;
import com.github.goodfatcat.orderservice.dto.OrderRequest;
import com.github.goodfatcat.orderservice.event.OrderPlacedEvent;
import com.github.goodfatcat.orderservice.model.Order;
import com.github.goodfatcat.orderservice.model.OrderLineItems;
import com.github.goodfatcat.orderservice.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {

	private OrderRepository orderRepo;
	private WebClient.Builder webClientBuilder;
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;
	@Value("${rabbitmq.routing.key}")
	private String routingKey;
	
	@Autowired
	public OrderServiceImpl(OrderRepository orderRepo, Builder webClientBuilder,
			RabbitTemplate rabbitTemplate) {
		this.orderRepo = orderRepo;
		this.webClientBuilder = webClientBuilder;
		this.rabbitTemplate = rabbitTemplate;
	}

	public Order placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());

		List<OrderLineItems> orderLineItemsList = orderRequest
				.getOrderLineItemsDtos().stream().map(orderLineItemsDto -> {
					OrderLineItems orderLineItems = new OrderLineItems();
					orderLineItems.setPrice(orderLineItemsDto.getPrice());
					orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
					orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
					return orderLineItems;
				}).toList();

		order.setOrderLineItems(orderLineItemsList);

		List<String> skuCodes = order.getOrderLineItems().stream()
				.map(orderLineItem -> orderLineItem.getSkuCode()).toList();

		InventoryResponse[] inventoryResponsesArray = webClientBuilder.build().get()
				.uri("http://inventory-service/api/inventory",
						uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes)
								.build())
				.retrieve().bodyToMono(InventoryResponse[].class).block();
		
		boolean areAllProductsInStock = Arrays.stream(inventoryResponsesArray).allMatch(inventoryResponse -> inventoryResponse.isInStock());

		if (areAllProductsInStock) {
			Order savedOrder = orderRepo.save(order);
			log.info(savedOrder.getOrderNumber());
			rabbitTemplate.convertAndSend(exchangeName, routingKey, new OrderPlacedEvent(savedOrder.getOrderNumber()));
			return savedOrder;
		} else {
			throw new IllegalArgumentException(
					"Product is not in stock, please try again later");
		}

	}
}