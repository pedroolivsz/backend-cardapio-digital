package com.io.github.pedroolivsz.cardapio.food.service;

import com.io.github.pedroolivsz.cardapio.food.dto.FoodRequestDTO;
import com.io.github.pedroolivsz.cardapio.food.dto.FoodResponseDTO;
import com.io.github.pedroolivsz.cardapio.category.entity.Category;
import com.io.github.pedroolivsz.cardapio.food.entity.Food;
import com.io.github.pedroolivsz.cardapio.exceptions.BusinessException;
import com.io.github.pedroolivsz.cardapio.exceptions.ResourceNotFoundException;
import com.io.github.pedroolivsz.cardapio.food.mapper.FoodMapper;
import com.io.github.pedroolivsz.cardapio.category.repository.CategoryRepository;
import com.io.github.pedroolivsz.cardapio.food.repository.FoodRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    public FoodService(FoodRepository foodRepository, CategoryRepository categoryRepository) {
        this.foodRepository = foodRepository;
        this.categoryRepository = categoryRepository;
    }

    public void save(FoodRequestDTO foodRequestDTO) {
        validateSaveBusinessRules(foodRequestDTO);

        Category category = categoryRepository
                .findById(foodRequestDTO.categoryId())
                .orElseThrow();

        Food food = FoodMapper.toEntity(foodRequestDTO, category);
        foodRepository.save(food);
    }

    @Transactional
    public void update(Long id, FoodRequestDTO foodRequestDTO) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found."));

        validateUpdateBusinessRules(id, foodRequestDTO);

        food.setTitle(foodRequestDTO.title());
        food.setImage(foodRequestDTO.image());
        food.setDescription(foodRequestDTO.description());
        food.setPrice(foodRequestDTO.price());
        food.setStock(foodRequestDTO.stock());

        foodRepository.save(food);
    }

    public void delete(Long id) {
        validateDeleteBusinessRules(id);

        foodRepository.deleteById(id);
    }

    public List<FoodResponseDTO> getAll() {
        return foodRepository.findAll()
                .stream()
                .map(FoodResponseDTO::new)
                .toList();
    }

    private void validateSaveBusinessRules(FoodRequestDTO dto) {
        if(foodRepository.existsByTitle(dto.title())) {
            throw new BusinessException("Food already exists with is title.");
        }
    }

    private void validateDeleteBusinessRules(Long id) {
        if(!foodRepository.existsById(id)) {
            throw new BusinessException("Food not found.");
        }
    }

    private void validateUpdateBusinessRules(Long id, FoodRequestDTO dto) {
        boolean existsWithSameTitle =
                foodRepository.existsByTitleAndIdNot(dto.title(), id);

        if(existsWithSameTitle) {
            throw new BusinessException("Another food already uses this title.");
        }
    }
}
