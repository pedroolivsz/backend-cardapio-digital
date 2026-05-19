package com.io.github.pedroolivsz.cardapio.DTO.category;

import com.io.github.pedroolivsz.cardapio.entity.Category;

public record CategoryResponse(Long id, String name) {
    public CategoryResponse(Category category) {this(category.getId(), category.getName());}
}
