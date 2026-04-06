package com.io.github.pedroolivsz.cardapio.repository;

import com.io.github.pedroolivsz.cardapio.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
