package com.io.github.pedroolivsz.cardapio.menu.controller;

import com.io.github.pedroolivsz.cardapio.category.dto.CategoryMenu;
import com.io.github.pedroolivsz.cardapio.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/menu")
@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:3000"
        },
        allowedHeaders = "*"
)
@Tag(
        name = "Menu",
        description = "Operações relacionadas ao menu"
)
public class MenuController {
    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @Operation(
            summary = "Listar categorias e os alimentos relacionados a ela",
            description = "Retorna todos as categorias e alimentos cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de categorias e alimentos retornada com sucesso"
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryMenu> getMenu() {
        return service.getMenu();
    }
}
