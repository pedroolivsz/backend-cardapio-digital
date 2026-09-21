package com.io.github.pedroolivsz.cardapio.orderItem.dto;

import com.io.github.pedroolivsz.cardapio.orderItem.entity.OrderItem;

import java.math.BigDecimal;

public record OrderItemResponse(Long id,
                                String foodName,
                                Integer quantity,
                                BigDecimal price) {
    public OrderItemResponse(OrderItem item) {
        this(
                item.getId(),
                item.getFood() != null ? item.getFood().getTitle() : "Produto removido",
                item.getQuantity(),
                item.getPrice()
        );
    }
}
