package com.io.github.pedroolivsz.cardapio.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "foods")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String image;
    private BigDecimal price;
    private Integer stock;

    public Food(String title,
                String description,
                Category category,
                String image,
                BigDecimal price,
                Integer stock) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.image = image;
        this.price = price;
        this.stock = stock;
    }
}