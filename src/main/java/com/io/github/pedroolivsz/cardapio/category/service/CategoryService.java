package com.io.github.pedroolivsz.cardapio.category.service;

import com.io.github.pedroolivsz.cardapio.category.dto.CategoryRequest;
import com.io.github.pedroolivsz.cardapio.category.dto.CategoryResponse;
import com.io.github.pedroolivsz.cardapio.exceptions.BusinessException;
import com.io.github.pedroolivsz.cardapio.category.mapper.CategoryMapper;
import com.io.github.pedroolivsz.cardapio.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

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
     * @param categoryRequest dados da categoria
     */
    public void save(CategoryRequest categoryRequest) {
        validateSaveRulesBusiness(categoryRequest);
        repository.save(CategoryMapper.toEntity(categoryRequest));
    }

    public List<CategoryResponse> getAll() {
        return repository.findAll()
                .stream()
                .map(CategoryResponse::new)
                .toList();
    }

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
            throw new BusinessException("Category not found");
        }
    }
}
