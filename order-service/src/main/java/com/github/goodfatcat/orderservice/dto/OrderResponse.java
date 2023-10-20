package com.github.goodfatcat.orderservice.dto;

import java.util.List;

import com.github.goodfatcat.orderservice.model.OrderLineItems;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private List<OrderLineItems> orderLineItems;
}
