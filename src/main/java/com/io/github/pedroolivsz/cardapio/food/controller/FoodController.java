package com.io.github.pedroolivsz.cardapio.food.controller;

import com.io.github.pedroolivsz.cardapio.food.dto.FoodRequest;
import com.io.github.pedroolivsz.cardapio.food.dto.FoodResponse;
import com.io.github.pedroolivsz.cardapio.food.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:3000"
        },
        allowedHeaders = "*"
)
@Tag(
    name = "Food",
    description = "Operações relacionadas aos alimentos"
)
public class FoodController {
    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @Operation(
            summary = "Cadastrar alimento",
            description = "Cadastra um novo alimento no sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Alimento cadastrado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            )
    })
    @PostMapping
    public void saveFood(@RequestBody FoodRequest foodRequest) {
        service.save(foodRequest);
    }

    @Operation(
            summary = "Listar alimentos",
            description = "Retorna todos os alimentos cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Lista de alimentos retornada com sucesso"
    )
    @GetMapping
    public List<FoodResponse> getAll() {
        return service.getAll();
    }

    @Operation(
            summary = "Atualizar alimento",
            description = "Atualiza os dados de um alimento existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Alimento atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alimento não encontrado"
            )
    })
    @PutMapping("/{id}")
    public void update(
            @Parameter(
                    description = "Identificador do alimento",
                    example = "1",
                    required = true,
                    in = ParameterIn.PATH
            )
            @PathVariable Long id,
            @RequestBody @Valid FoodRequest foodRequest) {
        service.update(id, foodRequest);
    }

    @Operation(
            summary = "Excluir alimento",
            description = "Remove um alimento do sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Alimento excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alimento não encontrado"
            )
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(
                    description = "Identificador do alimento",
                    example = "1",
                    required = true,
                    in = ParameterIn.PATH
            )
            @PathVariable Long id) {
        service.delete(id);
    }
}
