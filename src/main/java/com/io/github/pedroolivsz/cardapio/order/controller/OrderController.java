package com.io.github.pedroolivsz.cardapio.order.controller;

import com.io.github.pedroolivsz.cardapio.order.dto.OrderRequest;
import com.io.github.pedroolivsz.cardapio.order.dto.OrderResponse;
import com.io.github.pedroolivsz.cardapio.order.enums.OrderStatus;
import com.io.github.pedroolivsz.cardapio.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowedHeaders = "*")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public void createOrder(@RequestBody @Valid OrderRequest orderRequest) {
        orderService.createOrder(orderRequest);
    }

    @GetMapping
    public List<OrderResponse> listAllOrders() {
        return orderService.listAllOrders();
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id,
                                             @RequestParam OrderStatus status) {

        orderService.updateStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
