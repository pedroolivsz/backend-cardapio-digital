package com.io.github.pedroolivsz.cardapio.category.service;

import com.io.github.pedroolivsz.cardapio.category.dto.CategoryRequest;
import com.io.github.pedroolivsz.cardapio.category.dto.CategoryResponse;
import com.io.github.pedroolivsz.cardapio.category.entity.Category;
import com.io.github.pedroolivsz.cardapio.exceptions.BusinessException;
import com.io.github.pedroolivsz.cardapio.category.mapper.CategoryMapper;
import com.io.github.pedroolivsz.cardapio.category.repository.CategoryRepository;
import com.io.github.pedroolivsz.cardapio.exceptions.CategoryNotFoundException;
import com.io.github.pedroolivsz.cardapio.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    /**
     * Cadastra uma nova categoria após validar
     * as regras de negócio.
     *
     * @param request dados da categoria a ser cadastrada
     * @return dados da categoria cadastrada
     * @throws BusinessException se já existir uma categoria com mesmo nome
     */
    public CategoryResponse save(CategoryRequest request) {
        validateSaveRulesBusiness(request);

        Category category = CategoryMapper.toEntity(request);
        repository.save(category);

        return CategoryMapper.toResponse(category);
    }

    /**
     * Atualiza os dados de uma categoria após validar as regras de negócio
     *
     * @param id identificador da categoria a ser atualizada
     * @param request novos dados da categoria
     * @return dados da categoria atualizados
     * @throws BusinessException se outra categoria já tiver o mesmo nome
     * @throws CategoryNotFoundException se a categoria a ser atualizada não existir
     */
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        validateSaveRulesBusiness(request);

        Category category = repository.findById(id)
                        .orElseThrow(() -> new CategoryNotFoundException(id));

        category.setName(request.name());

        return CategoryMapper.toResponse(category);
    }

    /**
     * Retorna todas as categorias cadastradas.
     *
     * @return lista com os dados de todos as categorias
     */
    public List<CategoryResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(CategoryResponse::new)
                .toList();
    }

    /**
     * Retorna uma categoria pelo seu id
     *
     * @param id da categoria
     * @return categoria procurado
     * @throws CategoryNotFoundException se a categoria não existir
     */
    @Transactional(readOnly = true)
    public CategoryResponse getById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found category."));

        return CategoryMapper.toResponse(category);
    }

    /**
     * Remove uma categoria existente após validar as regras de negócio.
     *
     * @param id identificador da categoria a ser removida
     * @throws CategoryNotFoundException se a categoria não existir
     */
    public void delete(Long id) {
        validateDeleteRuleBusiness(id);
        repository.deleteById(id);
    }

    private void validateSaveRulesBusiness(CategoryRequest categoryRequest) {
        if(repository.existsByName(categoryRequest.name())) {
            throw new BusinessException("Category already exists this name.");
        }
    }

    private void validateDeleteRuleBusiness(Long id) {
        if(!repository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
    }
}
