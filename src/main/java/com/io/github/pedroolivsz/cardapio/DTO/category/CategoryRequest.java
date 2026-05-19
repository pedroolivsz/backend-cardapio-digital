package com.io.github.pedroolivsz.cardapio.DTO.category;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(@NotBlank(message = "Name is required")
                              String name) {
}
