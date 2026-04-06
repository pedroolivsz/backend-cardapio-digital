package com.io.github.pedroolivsz.cardapio.service;

import com.io.github.pedroolivsz.cardapio.DTO.OrderRequest;
import com.io.github.pedroolivsz.cardapio.DTO.OrderResponse;
import com.io.github.pedroolivsz.cardapio.entity.Food;
import com.io.github.pedroolivsz.cardapio.entity.Order;
import com.io.github.pedroolivsz.cardapio.entity.OrderItem;
import com.io.github.pedroolivsz.cardapio.entity.enums.OrderStatus;
import com.io.github.pedroolivsz.cardapio.entity.enums.PaymentMethod;
import com.io.github.pedroolivsz.cardapio.exceptions.ResourceNotFoundException;
import com.io.github.pedroolivsz.cardapio.repository.FoodRepository;
import com.io.github.pedroolivsz.cardapio.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public OrderService(OrderRepository orderRepository,
                        FoodRepository foodRepository,
                        SimpMessagingTemplate messagingTemplate) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Transactional
    public void createOrder(OrderRequest orderRequest) {
        if(orderRequest.items().isEmpty()) {
            throw new RuntimeException("Pedido sem itens");
        }

        if(orderRequest.paymentMethod() == null) {
            throw new RuntimeException("Forma de pagamento é obrigatória");
        }

        if(orderRequest.paymentMethod() == PaymentMethod.CASH) {
            if(orderRequest.changeFor() != null &&
            orderRequest.changeFor().compareTo(BigDecimal.ZERO) <= 0) {
                throw new RuntimeException("Valor de troco inválido");
            }
        } else {
            if(orderRequest.changeFor() != null) {
                throw new RuntimeException("Troco só é permitido para pagamento em dinheiro");
            }
        }

        Order order = new Order();
        order.setCustomerName(orderRequest.customerName());
        order.setCustomerPhone(orderRequest.customerPhone());
        order.setAddress(orderRequest.address());
        order.setObservation(orderRequest.observation());

        order.setPaymentMethod(orderRequest.paymentMethod());
        order.setChangeFor(orderRequest.changeFor());

        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.RECEIVED);

        List<OrderItem> items = orderRequest.items().stream().map(itemRequest -> {
            Food food = foodRepository.findById(itemRequest.foodId())
                    .orElseThrow(() -> new ResourceNotFoundException("Food not found"));

            if(food.getStock() < itemRequest.quantity()) {
                throw new RuntimeException("Estoque insuficiente para: " + food.getTitle());
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setFood(food);
            item.setQuantity(itemRequest.quantity());
            item.setPrice(food.getPrice());

            return item;
        }).toList();

        order.setItems(items);

        BigDecimal total = items.stream()
                        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        if(orderRequest.paymentMethod() == PaymentMethod.CASH &&
                orderRequest.changeFor() != null &&
                orderRequest.changeFor().compareTo(total) < 0) {
            throw new RuntimeException("Valor para troco menor que o total do pedido");
        }

        order.setTotal(total);

        items.forEach(item -> {
            Food food = item.getFood();
            food.setStock(food.getStock() - item.getQuantity());
        });

        orderRepository.save(order);

        messagingTemplate.convertAndSend("/topic/orders", new OrderResponse(order));
    }

    public List<OrderResponse> listAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));

        orderRepository.delete(order);
    }

    public void updateStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found."));
        if(order.getStatus() == OrderStatus.DELIVERED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido já finalizado");
        }
        order.setStatus(status);

        orderRepository.save(order);
        messagingTemplate.convertAndSend("/topic/orders/update", new OrderResponse(order));
    }
}
