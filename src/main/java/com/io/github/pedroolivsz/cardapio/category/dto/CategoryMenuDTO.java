package com.io.github.pedroolivsz.cardapio.category.dto;

import com.io.github.pedroolivsz.cardapio.food.dto.FoodMenuDTO;

import java.util.List;

public record CategoryMenuDTO(Long categoryId,
                              String categoryName,
                              List<FoodMenuDTO> foods) {
}
