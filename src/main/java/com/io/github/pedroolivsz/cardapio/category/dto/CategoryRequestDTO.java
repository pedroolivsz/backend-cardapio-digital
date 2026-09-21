package com.io.github.pedroolivsz.cardapio.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequestDTO(@NotBlank(message = "Name is required")
                              String name) {
}
