package com.io.github.pedroolivsz.cardapio.DTO;

public record OrderItemRequest(
        Long foodId,
        Integer quantity
) {}
