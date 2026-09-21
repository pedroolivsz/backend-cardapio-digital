package com.io.github.pedroolivsz.cardapio.food.controller;

import com.io.github.pedroolivsz.cardapio.food.dto.FoodRequestDTO;
import com.io.github.pedroolivsz.cardapio.food.dto.FoodResponseDTO;
import com.io.github.pedroolivsz.cardapio.food.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowedHeaders = "*")
public class FoodController {
    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @PostMapping
    public void saveFood(@RequestBody FoodRequestDTO foodRequestDTO) {
        service.save(foodRequestDTO);
    }

    @GetMapping
    public List<FoodResponseDTO> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody FoodRequestDTO foodRequestDTO) {
        service.update(id, foodRequestDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
