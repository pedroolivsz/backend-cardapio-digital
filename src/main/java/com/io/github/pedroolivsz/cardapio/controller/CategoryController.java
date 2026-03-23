package com.io.github.pedroolivsz.cardapio.controller;

import com.io.github.pedroolivsz.cardapio.DTO.CategoryRequest;
import com.io.github.pedroolivsz.cardapio.DTO.CategoryResponse;
import com.io.github.pedroolivsz.cardapio.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    public void save(@RequestBody CategoryRequest request) {
        service.save(request);
    }

    @GetMapping
    public List<CategoryResponse> getAll() {
        return service.getAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
