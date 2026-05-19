package com.io.github.pedroolivsz.cardapio.DTO.order;

public record OrderItemRequest(
        Long foodId,
        Integer quantity
) {}
