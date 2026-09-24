package com.io.github.pedroolivsz.cardapio.food.mapper;

import com.io.github.pedroolivsz.cardapio.food.dto.FoodRequest;
import com.io.github.pedroolivsz.cardapio.category.entity.Category;
import com.io.github.pedroolivsz.cardapio.food.entity.Food;

/**
 * Classe responsável por converter objetos relacionados
 * à entidade Food.
 */
public class FoodMapper {
    /**
     * Converte um FoodRequest em uma entidade Food.
     *
     * @param request dados do alimento recebidos pela API
     * @param category categoria associada ao alimento
     * @return entidade Food preenchida com os dados fornecidos
     */
    public static Food toEntity(FoodRequest request, Category category) {
        return new Food(
                request.title(),
                request.description(),
                category,
                request.image(),
                request.price(),
                request.stock()
        );
    }
}
