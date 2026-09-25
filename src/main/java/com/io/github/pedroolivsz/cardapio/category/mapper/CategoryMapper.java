package com.io.github.pedroolivsz.cardapio.category.mapper;

import com.io.github.pedroolivsz.cardapio.category.dto.CategoryRequest;
import com.io.github.pedroolivsz.cardapio.category.dto.CategoryResponse;
import com.io.github.pedroolivsz.cardapio.category.entity.Category;

/**
 * Classe responsável por converter objetos relacionados
 * à entidade Category.
 */
public class CategoryMapper {
    /**
     * Converte uma CategoryRequest em uma entidade Category.
     *
     * @param categoryRequest dados da categoria recebidos pela API
     * @return entidade Category preenchida com os dados fornecidos
     */
    public static Category toEntity(CategoryRequest categoryRequest) {
        return new Category(categoryRequest.name());
    }

    /**
     * Converte uma Category em uma entidade CategoryResponse.
     *
     * @param category dados da categoria recebidos
     * @return CategoryResponse preenchida com os dados fornecidos
     */
    public static CategoryResponse toResponse(Category category) {
        return new CategoryResponse(category.getId(), category.getName());
    }
}
