package com.io.github.pedroolivsz.cardapio.orderItem.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados necessários para criar um item da comanda")
public record OrderItemRequest(
        @Schema(
                description = "Identificador único do item",
                example = "1",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        Long foodId,
        @Schema(
                description = "Quantidade de itens",
                example = "5"
        )
        Integer quantity
) {}
