package com.io.github.pedroolivsz.cardapio.DTO.food;

import com.io.github.pedroolivsz.cardapio.entity.Food;

import java.math.BigDecimal;

public record FoodResponse(Long id,
                           String title,
                           String description,
                           Long categoryId,
                           String image,
                           BigDecimal price,
                           Integer stock) {
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
