package com.io.github.pedroolivsz.cardapio.category.controller;

import com.io.github.pedroolivsz.cardapio.category.dto.CategoryRequest;
import com.io.github.pedroolivsz.cardapio.category.dto.CategoryResponse;
import com.io.github.pedroolivsz.cardapio.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:3000"
        },
        allowedHeaders = "*"
)
@Tag(
        name = "Category",
        description = "Operações relacionadas as categorias"
)
public class CategoryController {
    private final CategoryService service;

    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar categoria",
            description = "Cadastra uma nova categoria no sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria cadastrada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            )
    })
    @PostMapping
    public void save(@RequestBody CategoryRequest request) {
        service.save(request);
    }

    @Operation(
            summary = "Listar categorias",
            description = "Retorna todas as categorias cadastradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de categorias retornada com sucesso"
    )
    @GetMapping
    public List<CategoryResponse> getAll() {
        return service.getAll();
    }

    @Operation(
            summary = "Excluir categoria",
            description = "Remove uma categoria do sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Categoria excluída com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada"
            )
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
