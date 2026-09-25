package com.io.github.pedroolivsz.cardapio.food.service;

import com.io.github.pedroolivsz.cardapio.exceptions.CategoryNotFoundException;
import com.io.github.pedroolivsz.cardapio.exceptions.FoodNotFoundException;
import com.io.github.pedroolivsz.cardapio.food.dto.FoodRequest;
import com.io.github.pedroolivsz.cardapio.food.dto.FoodResponse;
import com.io.github.pedroolivsz.cardapio.category.entity.Category;
import com.io.github.pedroolivsz.cardapio.food.entity.Food;
import com.io.github.pedroolivsz.cardapio.exceptions.BusinessException;
import com.io.github.pedroolivsz.cardapio.food.mapper.FoodMapper;
import com.io.github.pedroolivsz.cardapio.category.repository.CategoryRepository;
import com.io.github.pedroolivsz.cardapio.food.repository.FoodRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;
    private final CategoryRepository categoryRepository;

    public FoodService(FoodRepository foodRepository, CategoryRepository categoryRepository) {
        this.foodRepository = foodRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Cadastra um novo alimento após validar as regras de negócio.
     *
     * @param request dados do alimento a ser cadastrado
     * @return dados do alimento cadastrado
     * @throws BusinessException se já existir um alimento com o mesmo título
     * @throws CategoryNotFoundException se a categoria informada não existir
     */
    public FoodResponse save(FoodRequest request) {
        validateSaveBusinessRules(request);

        Category category = categoryRepository
                .findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));

        Food food = FoodMapper.toEntity(request, category);
        foodRepository.save(food);
        return new FoodResponse(food);
    }

    /**
     * Atualiza os dados de um alimento existente após validar as regras de negócio.
     *
     * @param id identificador do alimento a ser atualizado
     * @param request novos dados do alimento
     * @return dados do alimento atualizado
     * @throws FoodNotFoundException se o alimento não existir
     * @throws BusinessException se outro alimento já usar o mesmo título
     * @throws CategoryNotFoundException se a categoria informada não existir
     */
    @Transactional
    public FoodResponse update(Long id, FoodRequest request) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));

        validateUpdateBusinessRules(id, request);

        Category category = categoryRepository.findById(request.categoryId())
                        .orElseThrow(() -> new CategoryNotFoundException(request.categoryId()));

        food.setTitle(request.title());
        food.setImage(request.image());
        food.setCategory(category);
        food.setDescription(request.description());
        food.setPrice(request.price());
        food.setStock(request.stock());

        return new FoodResponse(food);
    }

    /**
     * Retorna todos os alimentos cadastrados.
     *
     * @return lista com os dados de todos os alimentos
     */
    @Transactional(readOnly = true)
    public List<FoodResponse> getAll() {
        return foodRepository.findAll()
                .stream()
                .map(FoodResponse::new)
                .toList();
    }

    /**
     * Retorna um alimento pelo seu id
     *
     * @param id do alimento
     * @return alimento procurado
     * @throws FoodNotFoundException se o alimento não existir
     */
    @Transactional(readOnly = true)
    public FoodResponse getById(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new FoodNotFoundException(id));
        return new FoodResponse(food);
    }

    /**
     * Remove um alimento existente após validar as regras de negócio.
     *
     * @param id identificador do alimento a ser removido
     * @throws FoodNotFoundException se o alimento não existir
     */
    public void delete(Long id) {
        try {
            foodRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new FoodNotFoundException(id);
        }
    }

    private void validateSaveBusinessRules(FoodRequest dto) {
        if(foodRepository.existsByTitle(dto.title())) {
            throw new BusinessException("Food already exists with is title.");
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
