package com.github.goodfatcat.orderservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.goodfatcat.orderservice.event.OrderPlacedEvent;

@SpringBootApplication
public class OrderServiceApplication {
	
	@Value("${rabbitmq.exchange.name}")
	private String exchangeName;
	@Value("${rabbitmq.routing.key}")
	private String routingKey;

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
	
	@Bean
	CommandLineRunner runner(RabbitTemplate rabbitTemplate) {
		return args -> {rabbitTemplate.convertAndSend(exchangeName, routingKey, new OrderPlacedEvent("hello with parameters"));
			
			rabbitTemplate.convertAndSend("myExchange", "myKey", new OrderPlacedEvent("Hello"));};
	}
}
