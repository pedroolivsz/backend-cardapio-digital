package com.io.github.pedroolivsz.cardapio.order.dto;

import com.io.github.pedroolivsz.cardapio.orderItem.dto.OrderItemResponse;
import com.io.github.pedroolivsz.cardapio.order.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.List;

@Schema(description = "Dados de resposta de um pedido")
public record OrderResponse(

        @Schema(
                description = "Identificador único do pedido",
                example = "1"
        )
        Long id,

        @Schema(
                description = "Nome do cliente",
                example = "João Pedro"
        )
        String customerName,

        @Schema(
                description = "Telefone do cliente",
                example = "88999999999"
        )
        String customerPhone,

        @Schema(
                description = "Endereço de entrega",
                example = "Rua Principal, 123"
        )
        String address,

        @Schema(
                description = "Observação do pedido",
                example = "Sem cebola"
        )
        String observation,

        @Schema(
                description = "Método de pagamento",
                example = "PIX"
        )
        String paymentMethod,

        @Schema(
                description = "Valor utilizado para calcular o troco",
                example = "50.00"
        )
        BigDecimal changeFor,

        @Schema(
                description = "Data e hora de criação do pedido",
                example = "2026-09-22T18:30:00"
        )
        String createdAt,

        @Schema(
                description = "Status atual do pedido",
                example = "PENDING"
        )
        String status,

        @Schema(
                description = "Valor total do pedido",
                example = "79.90"
        )
        BigDecimal total,

        @Schema(
                description = "Itens que compõem o pedido"
        )
        List<OrderItemResponse> items

) {
    public OrderResponse(Order order) {
        this(
                order.getId(),
                order.getCustomerName(),
                order.getCustomerPhone(),
                order.getAddress(),
                order.getObservation(),
                order.getPaymentMethod() != null
                ? order.getPaymentMethod().toString()
                : null,
                order.getChangeFor(),
                order.getCreatedAt().toString(),
                order.getStatus().toString(),
                order.getTotal(),
                order.getItems().stream()
                        .map(OrderItemResponse:: new)
                        .toList()
        );
    }
}
