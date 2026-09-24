package com.io.github.pedroolivsz.cardapio.orderItem.dto;

import com.io.github.pedroolivsz.cardapio.orderItem.entity.OrderItem;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados de resposta de um item da comanda")
public record OrderItemResponse(
        @Schema(
                description = "Identificador único do item",
                example = "1",
                accessMode = Schema.AccessMode.READ_ONLY
        )
        Long id,
        @Schema(
                description = "Nome do alimento",
                example = "Hambúguer"
        )
        String foodName,

        @Schema(
                description = "Quantidade de itens",
                example = "5"
        )
        Integer quantity,

        @Schema(
                description = "Valor do item no momento da compra",
                example = "29.90"
        )
        BigDecimal price
) {
    public OrderItemResponse(OrderItem item) {
        this(
                item.getId(),
                item.getFood() != null ? item.getFood().getTitle() : "Produto removido",
                item.getQuantity(),
                item.getPrice()
        );
    }
}
