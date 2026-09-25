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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/foods")
@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:3000"
        },
        allowedHeaders = "*"
)
@Tag(
    name = "Foods",
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
                    responseCode = "201",
                    description = "Alimento cadastrado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodResponse save(
            @RequestBody
            @Valid
            FoodRequest request
    ) {
        return service.save(request);
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
    @ResponseStatus(HttpStatus.OK)
    public List<FoodResponse> getAll() {
        return service.getAll();
    }

    @Operation(
            summary = "Buscar alimento por id",
            description = "Retorna o alimento cadastrado"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Alimento retornado com sucesso"
    )
    @ApiResponse(
            responseCode = "404",
            description = "Alimento não encontrado"
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FoodResponse getById(
            @Parameter(
                    description = "Identificador do alimento",
                    example = "1",
                    required = true,
                    in = ParameterIn.PATH
            )
            @PathVariable
            Long id
    ) {
        return service.getById(id);
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
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Categoria não encontrada"
            )
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FoodResponse update(
            @Parameter(
                    description = "Identificador do alimento",
                    example = "1",
                    required = true,
                    in = ParameterIn.PATH
            )
            @PathVariable Long id,
            @RequestBody
            @Valid
            FoodRequest request
    ) {
        return service.update(id, request);
    }

    @Operation(
            summary = "Excluir alimento",
            description = "Remove um alimento do sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Alimento excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alimento não encontrado"
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(
                    description = "Identificador do alimento",
                    example = "1",
                    required = true,
                    in = ParameterIn.PATH
            )
            @PathVariable
            Long id
    ) {
        service.delete(id);
    }
}
