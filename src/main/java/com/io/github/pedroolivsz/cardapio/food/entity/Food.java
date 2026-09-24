package com.io.github.pedroolivsz.cardapio.food.entity;

import com.io.github.pedroolivsz.cardapio.category.entity.Category;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Representa um alimento disponivel no sistema")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único do alimento",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Schema(
            description = "Nome do alimento",
            example = "Hambúguer artesanal"
    )
    private String title;

    @Schema(
            description = "Descrição detalhada do alimento",
            example = "Hambúguer artesanal com queijo e bacon"
    )
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Schema(
            description = "Categoria a qual o alimento pertence"
    )
    private Category category;

    @Column(columnDefinition = "TEXT", nullable = false)
    @Schema(
            description = "URL ou conteúdo referente à imagem do alimento",
            example = "https://example.com/images/hamburguer.jpg"
    )
    private String image;

    @Schema(
            description = "Preço do alimento",
            example = "29.90"
    )
    private BigDecimal price;

    @Schema(
            description = "Quantidade dispoível em estoque",
            example = "50"
    )
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