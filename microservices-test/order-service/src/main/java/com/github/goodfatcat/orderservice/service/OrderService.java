package com.github.goodfatcat.orderservice.service;

import com.github.goodfatcat.orderservice.dto.OrderRequest;
import com.github.goodfatcat.orderservice.model.Order;

public interface OrderService {
    Order placeOrder(OrderRequest orderRequest);
}
