package com.io.github.pedroolivsz.cardapio.food.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Dados de resposta do alimento para o menu")
public record FoodResponseMenu(
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
                description = "URL da imagem",
                example = "https://example.com/images/hamburguer.jpg"
        )
        String image,

        @Schema(
                description = "Preço do alimento",
                example = "15.90"
        )
        BigDecimal price
) { }
