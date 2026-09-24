package com.io.github.pedroolivsz.cardapio.category.dto;

import com.io.github.pedroolivsz.cardapio.food.dto.FoodResponseMenu;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Representa os dados de uma categoria para o menu")
public record CategoryMenu(
        @Schema(
                description = "Identificador da categoria",
                example = "1"
        )
        Long categoryId,
        @Schema(
                description = "Nome da categoria",
                example = "Bebidas"
        )
        String categoryName,
        @Schema(
                description = "Lista de alimentos que pertencem à categoria"
        )
        List<FoodResponseMenu> foods) {
}
