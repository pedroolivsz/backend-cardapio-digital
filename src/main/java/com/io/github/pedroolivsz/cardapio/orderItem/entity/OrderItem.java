package com.io.github.pedroolivsz.cardapio.orderItem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.io.github.pedroolivsz.cardapio.food.entity.Food;
import com.io.github.pedroolivsz.cardapio.order.entity.Order;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Representa um item da comanda")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único do item",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @Schema(
            description = "Identificador único da comanda",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_id")
    @JsonBackReference
    @Schema(
            description = "Identificador único do alimento",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Food food;

    @Schema(
            description = "Quantidade de itens",
            example = "5"
    )
    private Integer quantity;

    @Schema(
            description = "Valor do item no momento da compra",
            example = "29.90"
    )
    private BigDecimal price;
}
