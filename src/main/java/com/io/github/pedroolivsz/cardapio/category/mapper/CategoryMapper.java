package com.io.github.pedroolivsz.cardapio.category.mapper;

import com.io.github.pedroolivsz.cardapio.category.dto.CategoryRequestDTO;
import com.io.github.pedroolivsz.cardapio.category.entity.Category;

public class CategoryMapper {
    public static Category toEntity(CategoryRequestDTO categoryRequestDTO) {
        return new Category(categoryRequestDTO.name());
    }
}
