package com.io.github.pedroolivsz.cardapio.mapper;

import com.io.github.pedroolivsz.cardapio.DTO.category.CategoryRequest;
import com.io.github.pedroolivsz.cardapio.entity.Category;

public class CategoryMapper {
    public static Category toEntity(CategoryRequest categoryRequest) {
        return new Category(categoryRequest.name());
    }
}
