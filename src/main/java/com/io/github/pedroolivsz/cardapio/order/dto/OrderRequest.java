package com.io.github.pedroolivsz.cardapio.order.dto;

import com.io.github.pedroolivsz.cardapio.orderItem.dto.OrderItemRequest;
import com.io.github.pedroolivsz.cardapio.order.enums.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        @NotBlank
        @Size(min = 3, max = 100)
        String customerName,
        @NotBlank
        @Size(min = 3, max = 100)
        String customerPhone,
        @NotBlank
        @Size(min = 3, max = 100)
        String address,
        @Size(max = 500)
        String observation,
        @NotNull
        PaymentMethod paymentMethod,
        BigDecimal changeFor,
        List<OrderItemRequest> items
) {}