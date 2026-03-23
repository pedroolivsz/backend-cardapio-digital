package com.io.github.pedroolivsz.cardapio.mapper;

import com.io.github.pedroolivsz.cardapio.DTO.FoodRequest;
import com.io.github.pedroolivsz.cardapio.entity.Category;
import com.io.github.pedroolivsz.cardapio.entity.Food;

public class FoodMapper {
    public static Food toEntity(FoodRequest request, Category category) {
        return new Food(
                request.title(),
                request.description(),
                category,
                request.image(),
                request.price()
        );
    }
}
