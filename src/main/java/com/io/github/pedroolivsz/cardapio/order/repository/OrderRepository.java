package com.io.github.pedroolivsz.cardapio.order.repository;

import com.io.github.pedroolivsz.cardapio.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
