package com.io.github.pedroolivsz.cardapio.order.service;

import com.io.github.pedroolivsz.cardapio.event.OrderCreatedEvent;
import com.io.github.pedroolivsz.cardapio.event.OrderEventListener;
import com.io.github.pedroolivsz.cardapio.exceptions.BusinessException;
import com.io.github.pedroolivsz.cardapio.exceptions.FoodNotFoundException;
import com.io.github.pedroolivsz.cardapio.order.dto.OrderRequest;
import com.io.github.pedroolivsz.cardapio.order.dto.OrderResponse;
import com.io.github.pedroolivsz.cardapio.food.entity.Food;
import com.io.github.pedroolivsz.cardapio.order.entity.Order;
import com.io.github.pedroolivsz.cardapio.orderItem.dto.OrderItemRequest;
import com.io.github.pedroolivsz.cardapio.orderItem.entity.OrderItem;
import com.io.github.pedroolivsz.cardapio.order.enums.OrderStatus;
import com.io.github.pedroolivsz.cardapio.order.enums.PaymentMethod;
import com.io.github.pedroolivsz.cardapio.exceptions.ResourceNotFoundException;
import com.io.github.pedroolivsz.cardapio.food.repository.FoodRepository;
import com.io.github.pedroolivsz.cardapio.order.repository.OrderRepository;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final OrderEventListener orderEventListener;

    public OrderService(OrderRepository orderRepository,
                        FoodRepository foodRepository,
                        SimpMessagingTemplate messagingTemplate,
                        OrderEventListener orderEventListener) {
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
        this.messagingTemplate = messagingTemplate;
        this.orderEventListener = orderEventListener;
    }

    /**
     * Cria um novo pedido, valida os itens e a forma de pagamento,
     * calcula o total, atualiza o estoque dos alimentos e dispara
     * notificações após o commit da transação.
     *
     * @param request dados do pedido a ser criado
     * @return dados do pedido criado
     * @throws BusinessException se o pedido não tiver itens, a forma de pagamento
     *         for inválida ou o estoque de algum item for insuficiente
     * @throws FoodNotFoundException se algum alimento informado não existir
     */
    @Transactional
    public OrderResponse create(OrderRequest request) {
        validateOrderRequest(request);

        Order order = buildOrder(request);

        Map<Long, Food> foodsById = foodRepository
                .findAllById(request.items().stream().map(OrderItemRequest::foodId).toList())
                .stream()
                .collect(Collectors.toMap(Food::getId, Function.identity()));

        List<OrderItem> items = request.items().stream().map(itemRequest -> {
            Food food = foodsById.get(itemRequest.foodId());
            if(food == null) throw new FoodNotFoundException(itemRequest.foodId());
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

        validateChangeFor(request, total);

        order.setTotal(total);

        items.forEach(item -> {
            Food food = item.getFood();
            food.setStock(food.getStock() - item.getQuantity());
        });

        orderRepository.save(order);

        OrderResponse response = new OrderResponse(order);
        orderEventListener.onOrderCreated(new OrderCreatedEvent(response));

        return response;
    }

        public List<OrderResponse> listAll() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::new)
                .toList();
    }

    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found order."));

        return new OrderResponse(order);
    }

    public void delete(Long id) {
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

    private void validateOrderRequest(OrderRequest request) {
        if(request.items() == null || request.items().isEmpty()) {
            throw new BusinessException("Pedido sem itens");
        }

        if(request.paymentMethod() == null) {
            throw new BusinessException("Forma de pagamento é obrigatória");
        }

        if(request.paymentMethod() != PaymentMethod.CASH && request.changeFor() != null) {
            throw new BusinessException("Troco só é permitido para pagamento em dinheiro.");
        }
    }

    private Order buildOrder(OrderRequest request) {
        return new Order(
                request.customerName(),
                request.customerPhone(),
                request.address(),
                request.observation(),
                request.paymentMethod(),
                request.changeFor(),
                LocalDateTime.now(),
                OrderStatus.RECEIVED
        );
    }

    private void validateChangeFor(OrderRequest request, BigDecimal total) {
        if (request.paymentMethod() != PaymentMethod.CASH || request.changeFor() == null) {
            return;
        }
        if (request.changeFor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("Valor de troco inválido.");
        }
        if (request.changeFor().compareTo(total) < 0) {
            throw new BusinessException("Valor para troco menor que o total do pedido.");
        }
    }
}