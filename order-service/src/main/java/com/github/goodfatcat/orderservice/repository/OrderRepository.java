package com.github.goodfatcat.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.goodfatcat.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
