package com.io.github.pedroolivsz.cardapio.repository;

import com.io.github.pedroolivsz.cardapio.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
    boolean existsByTitle(String title);
    boolean existsByTitleAndIdNot(String title, Long id);
    boolean existsById(Long id);
}
