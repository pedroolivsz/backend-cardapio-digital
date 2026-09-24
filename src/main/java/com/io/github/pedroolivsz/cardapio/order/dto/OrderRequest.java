package com.io.github.pedroolivsz.cardapio.order.dto;

import com.io.github.pedroolivsz.cardapio.orderItem.dto.OrderItemRequest;
import com.io.github.pedroolivsz.cardapio.order.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Dados necessários para cadastrar uma comanda")
public record OrderRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        @Schema(description = "Nome do cliente", example = "Nicolas")
        String customerName,

        @NotBlank
        @Size(min = 3, max = 100)
        @Schema(description = "Número do cliente", example = "88999999999")
        String customerPhone,
        @NotBlank
        @Size(min = 3, max = 100)
        @Schema(description = "Endereço do cliente", example = "Rua dos Banzeiros, Nº676")
        String address,

        @Size(max = 500)
        @Schema(description = "Observação sobre o pedido", example = "Sem cebola")
        String observation,

        @NotNull
        @Schema(description = "Método de pagamento do pedido", example = "Pix")
        PaymentMethod paymentMethod,

        @Schema(description = "Valor para troco, caso necessário", example = "4.50")
        BigDecimal changeFor,

        @Schema(description = "Lista de produtos do pedido", example = "Hambúrguer, Pizza")
        List<OrderItemRequest> items
) {}