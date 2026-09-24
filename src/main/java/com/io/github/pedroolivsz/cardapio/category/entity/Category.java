package com.io.github.pedroolivsz.cardapio.category.entity;

import com.io.github.pedroolivsz.cardapio.food.entity.Food;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Representa uma categoria dentro do sistema")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador da categoria",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "Nome da categoria",
            example = "Bebidas"
    )
    private String name;

    @OneToMany(mappedBy = "category")
    @Schema(
            description = "Lista de alimentos que pertencem à categoria"
    )
    private List<Food> foods;

    public Category(String name) {
        this.name = name;
    }
}
