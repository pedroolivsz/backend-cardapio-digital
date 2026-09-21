package com.io.github.pedroolivsz.cardapio.order.dto;

import com.io.github.pedroolivsz.cardapio.orderItem.dto.OrderItemResponse;
import com.io.github.pedroolivsz.cardapio.order.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public record OrderResponse(
        Long id,
        String customerName,
        String customerPhone,
        String address,
        String observation,
        String paymentMethod,
        BigDecimal changeFor,
        String createdAt,
        String status,
        BigDecimal total,
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
