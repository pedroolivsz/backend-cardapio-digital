package com.io.github.pedroolivsz.cardapio.category.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Representa os dados necessaŕios para cria uma categoria")
public record CategoryRequest(
        @NotBlank(message = "Name is required")
        @Schema(
                description = "Nome da categoria",
                example = "Bebidas"
        )
        String name
) { }
