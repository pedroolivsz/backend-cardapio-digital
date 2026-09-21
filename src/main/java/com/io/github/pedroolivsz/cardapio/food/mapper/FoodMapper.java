package com.io.github.pedroolivsz.cardapio.food.mapper;

import com.io.github.pedroolivsz.cardapio.food.dto.FoodRequestDTO;
import com.io.github.pedroolivsz.cardapio.category.entity.Category;
import com.io.github.pedroolivsz.cardapio.food.entity.Food;

public class FoodMapper {
    public static Food toEntity(FoodRequestDTO request, Category category) {
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
