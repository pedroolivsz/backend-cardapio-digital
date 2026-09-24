package com.io.github.pedroolivsz.cardapio.food.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Schema(description = "Dados necessários para cadastro de um alimento")
public record FoodRequest(
        @NotBlank(message = "Title is required")
        @Size(min = 3, max = 100)
        @Schema(description = "Nome do alimento", example = "Habúrguer")
        String title,

        @NotBlank(message = "Description is required")
        @Schema(description = "Descrição do alimento", example = "Habúrguer artesanal com queijo")
        String description,

        @NotNull(message = "Category is required")
        @Schema(description = "Identificador da categoria", example = "1")
        Long categoryId,

        @NotBlank(message = "Image URL is required")
        @Schema(description = "URL da imagem", example = "https://example.com/images/hamburguer.jpg")
        String image,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be greater than zero")
        @Schema(description = "Preço do alimento", example = "29.90")
        BigDecimal price,
        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock must be greater than zero")
        @Schema(description = "Quantidade em estoque do produto", example = "50")
        Integer stock
) {}
