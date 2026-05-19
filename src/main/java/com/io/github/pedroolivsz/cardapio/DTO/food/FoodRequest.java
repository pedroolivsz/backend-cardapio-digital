package com.io.github.pedroolivsz.cardapio.DTO.food;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record FoodRequest(@NotBlank(message = "Title is required")
                          @Size(min = 3, max = 100)
                          String title,
                          @NotBlank(message = "Description is required")
                          String description,
                          @NotNull(message = "Category is required")
                          Long categoryId,
                          @NotBlank(message = "Image URL is required")
                          String image,
                          @NotNull(message = "Price is required")
                          @Positive(message = "Price must be greater than zero")
                          BigDecimal price,
                          @NotNull(message = "Stock is required")
                          @PositiveOrZero(message = "Stock must be greater than zero")
                          Integer stock) {
}
