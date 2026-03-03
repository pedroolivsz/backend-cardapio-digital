package com.io.github.pedroolivsz.cardapio.controller;

import com.io.github.pedroolivsz.cardapio.DTO.FoodRequestDTO;
import com.io.github.pedroolivsz.cardapio.DTO.FoodResponseDTO;
import com.io.github.pedroolivsz.cardapio.service.FoodService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/create")
    public void saveFood(@RequestBody FoodRequestDTO foodRequestDTO) {
        service.save(foodRequestDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/getAll")
    public List<FoodResponseDTO> getAll() {
        return service.getAll();
    }
}
