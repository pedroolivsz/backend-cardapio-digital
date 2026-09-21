package com.io.github.pedroolivsz.cardapio.food.dto;

import com.io.github.pedroolivsz.cardapio.food.entity.Food;

import java.math.BigDecimal;

public record FoodResponseDTO(Long id,
                              String title,
                              String description,
                              Long categoryId,
                              String image,
                              BigDecimal price,
                              Integer stock) {
    public FoodResponseDTO(Food food) {
        this(food.getId(),
                food.getTitle(),
                food.getDescription(),
                food.getCategory().getId(),
                food.getImage(),
                food.getPrice(),
                food.getStock());
    }
}
