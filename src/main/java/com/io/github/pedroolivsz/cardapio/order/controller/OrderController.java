package com.io.github.pedroolivsz.cardapio.order.controller;

import com.io.github.pedroolivsz.cardapio.order.dto.OrderRequest;
import com.io.github.pedroolivsz.cardapio.order.dto.OrderResponse;
import com.io.github.pedroolivsz.cardapio.order.enums.OrderStatus;
import com.io.github.pedroolivsz.cardapio.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(
        origins = {
                "http://localhost:5173",
                "http://localhost:3000"
        },
        allowedHeaders = "*"
)
@Tag(
        name = "Orders",
        description = "Operações relacionadas aos pedidos"
)
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "Criar pedido",
            description = """
                Cria um novo pedido.

                Regras:
                - O pedido deve conter pelo menos um item.
                - A forma de pagamento é obrigatória.
                - O estoque deve ser suficiente.
                - O troco deve ser compatível com o total.
                """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido criado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos ou regras de negócio violadas"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Alimento não encontrado"
            )
    })
    @PostMapping
    public void createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
    }

    @Operation(
            summary = "Listar pedidos",
            description = "Retorna todos os pedidos cadastrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Pedidos retornados com sucesso"
    )
    @GetMapping
    public List<OrderResponse> listAllOrders() {
        return orderService.listAllOrders();
    }

    @Operation(
            summary = "Excluir pedido",
            description = "Remove um pedido do sistema"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Pedido excluído com sucesso"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado"
            )
    })
    @DeleteMapping("/{id}")
    public void deleteOrder(
            @Parameter(
                    description = "Identificador do pedido",
                    example = "1",
                    required = true
            )
            @PathVariable
            Long id
    ) {
        orderService.deleteOrder(id);
    }

    @Operation(
            summary = "Atualizar status do pedido",
            description = "Atualiza o status de um pedido existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Status atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Status inválido"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pedido não encontrado"
            )
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @Parameter(
                    description = "Identificador do pedido",
                    example = "1",
                    required = true
            )
            @PathVariable Long id,
            @Parameter(
                    description = "Novo status do pedido",
                    example = "CONFIRMED",
                    required = true
            )
            @RequestParam OrderStatus status) {

        orderService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
