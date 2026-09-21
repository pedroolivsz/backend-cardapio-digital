package com.io.github.pedroolivsz.cardapio.orderItem.dto;

public record OrderItemRequest(
        Long foodId,
        Integer quantity
) {}
