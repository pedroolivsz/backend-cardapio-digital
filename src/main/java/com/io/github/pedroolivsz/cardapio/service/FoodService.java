package com.io.github.pedroolivsz.cardapio.service;

import com.io.github.pedroolivsz.cardapio.DTO.FoodRequest;
import com.io.github.pedroolivsz.cardapio.DTO.FoodResponse;
import com.io.github.pedroolivsz.cardapio.entity.Category;
import com.io.github.pedroolivsz.cardapio.entity.Food;
import com.io.github.pedroolivsz.cardapio.exceptions.BusinessException;
import com.io.github.pedroolivsz.cardapio.exceptions.ResourceNotFoundException;
import com.io.github.pedroolivsz.cardapio.mapper.FoodMapper;
import com.io.github.pedroolivsz.cardapio.repository.CategoryRepository;
import com.io.github.pedroolivsz.cardapio.repository.FoodRepository;
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

    public void save(FoodRequest foodRequest) {
        validateSaveBusinessRules(foodRequest);

        Category category = categoryRepository
                .findById(foodRequest.categoryId())
                .orElseThrow();

        Food food = FoodMapper.toEntity(foodRequest, category);
        foodRepository.save(food);
    }

    @Transactional
    public void update(Long id, FoodRequest foodRequest) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Food not found."));

        validateUpdateBusinessRules(id, foodRequest);

        food.setTitle(foodRequest.title());
        food.setImage(foodRequest.image());
        food.setDescription(foodRequest.description());
        food.setPrice(foodRequest.price());
        food.setStock(foodRequest.stock());

        foodRepository.save(food);
    }

    public void delete(Long id) {
        validateDeleteBusinessRules(id);

        foodRepository.deleteById(id);
    }

    public List<FoodResponse> getAll() {
        return foodRepository.findAll()
                .stream()
                .map(FoodResponse::new)
                .toList();
    }

    private void validateSaveBusinessRules(FoodRequest dto) {
        if(foodRepository.existsByTitle(dto.title())) {
            throw new BusinessException("Food already exists with is title.");
        }
    }

    private void validateDeleteBusinessRules(Long id) {
        if(!foodRepository.existsById(id)) {
            throw new BusinessException("Food not found.");
        }
    }

    private void validateUpdateBusinessRules(Long id, FoodRequest dto) {
        boolean existsWithSameTitle =
                foodRepository.existsByTitleAndIdNot(dto.title(), id);

        if(existsWithSameTitle) {
            throw new BusinessException("Another food already uses this title.");
        }
    }
}
