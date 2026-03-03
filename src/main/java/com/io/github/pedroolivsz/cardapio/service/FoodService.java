package com.io.github.pedroolivsz.cardapio.service;

import com.io.github.pedroolivsz.cardapio.DTO.FoodRequestDTO;
import com.io.github.pedroolivsz.cardapio.DTO.FoodResponseDTO;
import com.io.github.pedroolivsz.cardapio.entity.Food;
import com.io.github.pedroolivsz.cardapio.repository.FoodRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepository repository;

    public FoodService(FoodRepository repository) {
        this.repository = repository;
    }

    public void save(FoodRequestDTO foodRequestDTO) {
        Food food = toEntity(foodRequestDTO);
        repository.save(food);
    }

    public List<FoodResponseDTO> getAll() {
        return repository.findAll().stream().map(FoodResponseDTO::new).toList();
    }

    private Food toEntity(FoodRequestDTO request) {
        return new Food(
                request.title(),
                request.image(),
                request.price()
        );
    }
}
