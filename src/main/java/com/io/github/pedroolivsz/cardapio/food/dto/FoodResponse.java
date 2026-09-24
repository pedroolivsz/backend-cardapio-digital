package com.io.github.pedroolivsz.cardapio.food.dto;

import com.io.github.pedroolivsz.cardapio.food.entity.Food;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados de resposta de um alimento")
public record FoodResponse(
        @Schema(
                description = "Identificador do alimento",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nome do alimento",
                example = "Hambúrguer"
        )
        String title,

        @Schema(
                description = "Descrição do alimento",
                example = "Hambúrguer artesanal com queijo"
        )
        String description,

        @Schema(
                description = "Identificador da categoria",
                example = "1"
        )
        Long categoryId,

        @Schema(
                description = "URL da imagem",
                example = "https://example.com/images/hamburguer.jpg"
        )
        String image,

        @Schema(
                description = "Preço do alimento",
                example = "15.90"
        )
        BigDecimal price,

        @Schema(
                description = "Quantidade em estoque",
                example = "50"
        )
        Integer stock
) {
    public FoodResponse(Food food) {
        this(food.getId(),
                food.getTitle(),
                food.getDescription(),
                food.getCategory().getId(),
                food.getImage(),
                food.getPrice(),
                food.getStock());
    }
}
