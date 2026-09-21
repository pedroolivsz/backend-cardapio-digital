package com.io.github.pedroolivsz.cardapio.category.service;

import com.io.github.pedroolivsz.cardapio.category.dto.CategoryRequestDTO;
import com.io.github.pedroolivsz.cardapio.category.dto.CategoryResponseDTO;
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

    public void save(CategoryRequestDTO categoryRequestDTO) {
        validateSaveRulesBusiness(categoryRequestDTO);
        repository.save(CategoryMapper.toEntity(categoryRequestDTO));
    }

    public List<CategoryResponseDTO> getAll() {
        return repository.findAll()
                .stream()
                .map(CategoryResponseDTO::new)
                .toList();
    }

    public void delete(Long id) {
        validateDeleteRuleBusiness(id);
        repository.deleteById(id);
    }

    private void validateSaveRulesBusiness(CategoryRequestDTO categoryRequestDTO) {
        if(repository.existsByName(categoryRequestDTO.name())) {
            throw new BusinessException("Category already exists this name.");
        }
    }

    private void validateDeleteRuleBusiness(Long id) {
        if(!repository.existsById(id)) {
            throw new BusinessException("Category not found");
        }
    }
}
