package com.io.github.pedroolivsz.cardapio.menu.service;

import com.io.github.pedroolivsz.cardapio.category.dto.CategoryMenuDTO;
import com.io.github.pedroolivsz.cardapio.food.dto.FoodMenuDTO;
import com.io.github.pedroolivsz.cardapio.category.entity.Category;
import com.io.github.pedroolivsz.cardapio.category.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {
    private final CategoryRepository categoryRepository;

    public MenuService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryMenuDTO> getMenu() {
        List<Category> categories = categoryRepository.findAllWithFoods();

        return categories.stream()
                .map(category -> new CategoryMenuDTO(
                        category.getId(),
                        category.getName(),
                        category.getFoods().stream()
                                .map(food -> new FoodMenuDTO(
                                        food.getId(),
                                        food.getTitle(),
                                        food.getDescription(),
                                        food.getImage(),
                                        food.getPrice()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }
}
