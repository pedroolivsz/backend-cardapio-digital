package com.io.github.pedroolivsz.cardapio.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record FoodRequest(@NotBlank(message = "Title is required")
                             @Size(min = 3, max = 100)
                             String title,
                          @NotBlank(message = "Description is required")
                             String description,
                          @NotBlank(message = "Category is required")
                            Long categoryId,
                          @NotBlank(message = "Image URL is required")
                             String image,
                          @NotNull(message = "Price is required")
                             @Positive(message = "Price must be greater than zero")
                             BigDecimal price) {
}
