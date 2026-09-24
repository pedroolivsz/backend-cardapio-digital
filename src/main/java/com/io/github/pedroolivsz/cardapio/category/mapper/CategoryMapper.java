package com.io.github.pedroolivsz.cardapio.category.mapper;

import com.io.github.pedroolivsz.cardapio.category.dto.CategoryRequest;
import com.io.github.pedroolivsz.cardapio.category.entity.Category;

/**
 * Classe responsável por converter objetos relacionados
 * à entidade Category.
 */
public class CategoryMapper {
    /**
     * Converte um FoodRequest em uma entidade Food.
     *
     * @param categoryRequest dados da categoria recebidos pela API
     * @return entidade Category preenchida com os dados fornecidos
     */
    public static Category toEntity(CategoryRequest categoryRequest) {
        return new Category(categoryRequest.name());
    }
}
