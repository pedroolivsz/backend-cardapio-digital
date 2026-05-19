package com.io.github.pedroolivsz.cardapio.DTO.category;

import com.io.github.pedroolivsz.cardapio.DTO.food.FoodMenuDTO;

import java.util.List;

public record CategoryMenuDTO(Long categoryId,
                              String categoryName,
                              List<FoodMenuDTO> foods) {
}
