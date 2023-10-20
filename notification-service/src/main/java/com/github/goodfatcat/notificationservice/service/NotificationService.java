package com.github.goodfatcat.notificationservice.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.github.goodfatcat.notificationservice.event.OrderPlacedEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationService {
	
	@RabbitListener(queues = {"${rabbitmq.queue.name}"})
	private void sendNotification(OrderPlacedEvent event) {
		log.info("Sended notification to user order number: {}",  event.toString());
	}
}
