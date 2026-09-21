package com.io.github.pedroolivsz.cardapio.food.dto;

import java.math.BigDecimal;

public record FoodMenuDTO(Long id,
                          String title,
                          String description,
                          String image,
                          BigDecimal price) {
}
