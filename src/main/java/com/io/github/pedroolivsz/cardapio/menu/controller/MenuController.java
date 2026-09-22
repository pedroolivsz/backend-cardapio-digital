package com.io.github.pedroolivsz.cardapio.menu.controller;

import com.io.github.pedroolivsz.cardapio.category.dto.CategoryMenuDTO;
import com.io.github.pedroolivsz.cardapio.menu.service.MenuService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowedHeaders = "*")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public List<CategoryMenuDTO> getMenu() {
        return menuService.getMenu();
    }
}
