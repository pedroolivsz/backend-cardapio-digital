package com.io.github.pedroolivsz.cardapio.orderItem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.io.github.pedroolivsz.cardapio.food.entity.Food;
import com.io.github.pedroolivsz.cardapio.order.entity.Order;
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
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    @ManyToOne
    @JoinColumn(name = "food_id")
    @JsonBackReference
    private Food food;
    private Integer quantity;
    private BigDecimal price;
}
