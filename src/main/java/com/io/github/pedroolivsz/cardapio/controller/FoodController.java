package com.io.github.pedroolivsz.cardapio.controller;

import com.io.github.pedroolivsz.cardapio.DTO.FoodRequest;
import com.io.github.pedroolivsz.cardapio.DTO.FoodResponse;
import com.io.github.pedroolivsz.cardapio.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class FoodController {
    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @PostMapping
    public void saveFood(@RequestBody FoodRequest foodRequest) {
        service.save(foodRequest);
    }

    @GetMapping
    public List<FoodResponse> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody FoodRequest foodRequest) {
        service.update(id, foodRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
