package com.io.github.pedroolivsz.cardapio.order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.io.github.pedroolivsz.cardapio.orderItem.entity.OrderItem;
import com.io.github.pedroolivsz.cardapio.order.enums.OrderStatus;
import com.io.github.pedroolivsz.cardapio.order.enums.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Representa uma comanda no sistema")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador da comanda",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Nome do cliente",
            example = "Pablo"
    )
    private String customerName;

    @Schema(
            description = "Número do cliente",
            example = "88999999999"
    )
    private String customerPhone;

    @Schema(
            description = "Endereço para entrega",
            example = "Rua dos Banzeiros, Nº 676"
    )
    private String address;

    @Column(length = 500)
    @Schema(
            description = "Observação sobre o pedido",
            example = "Sem cebola"
    )
    private String observation;

    @Enumerated(value = EnumType.STRING)
    @Schema(
            description = "Método de pagamento do pedido",
            example = "Pix"
    )
    private PaymentMethod paymentMethod;

    @Schema(
            description = "Troco se necessário",
            example = "4.50"
    )
    private BigDecimal changeFor;

    @Schema(
            description = "Momento de criação da comanda",
            example = "2026-09-22T14:30:00"
    )
    private LocalDateTime createdAt;

    @Enumerated(value = EnumType.STRING)
    @Schema(
            description = "Status do pedido",
            example = "PREPARING"
    )
    private OrderStatus status;

    @Schema(
            description = "Valor total do pedido",
            example = "49.90"
    )
    private BigDecimal total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Schema(
            description = "Lista de produtos da comanda",
            example = "Hambúrguer, Pizza 4 queijos"
    )
    private List<OrderItem> items;

    public Order(String customerName,
                 String customerPhone,
                 String address,
                 LocalDateTime createdAt,
                 OrderStatus status
                 ) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.address = address;
        this.createdAt = createdAt;
        this.status = status;
    }

    public Order(String customerName,
                 String customerPhone,
                 String address,
                 String observation,
                 PaymentMethod paymentMethod,
                 BigDecimal changeFor,
                 LocalDateTime createdAt,
                 OrderStatus status
    ) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.address = address;
        this.observation = observation;
        this.paymentMethod = paymentMethod;
        this.changeFor = changeFor;
        this.createdAt = createdAt;
        this.status = status;
    }
}
