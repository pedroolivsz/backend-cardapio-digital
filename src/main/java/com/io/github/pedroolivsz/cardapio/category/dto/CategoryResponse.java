package com.io.github.pedroolivsz.cardapio.category.dto;

import com.io.github.pedroolivsz.cardapio.category.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de resposta de uma categoria")
public record CategoryResponse(
        @Schema(
                description = "Identificador da categoria",
                example = "1"
        )
        Long id,
        @Schema(
                description = "Nome da categoria",
                example = "Bebidas"
        )
        String name) {
    public CategoryResponse(Category category) {this(category.getId(), category.getName());}
}
