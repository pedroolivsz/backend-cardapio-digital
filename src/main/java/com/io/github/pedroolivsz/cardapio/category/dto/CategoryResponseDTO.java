package com.io.github.pedroolivsz.cardapio.category.dto;

import com.io.github.pedroolivsz.cardapio.category.entity.Category;

public record CategoryResponseDTO(Long id, String name) {
    public CategoryResponseDTO(Category category) {this(category.getId(), category.getName());}
}
