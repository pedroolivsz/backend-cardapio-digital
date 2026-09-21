package com.io.github.pedroolivsz.cardapio.orderItem.repository;

import com.io.github.pedroolivsz.cardapio.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
