package com.io.github.pedroolivsz.cardapio.category.repository;

import com.io.github.pedroolivsz.cardapio.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);

    @Query("""
    SELECT DISTINCT c
    FROM Category c
    LEFT JOIN FETCH c.foods
    """)
    List<Category> findAllWithFoods();
}