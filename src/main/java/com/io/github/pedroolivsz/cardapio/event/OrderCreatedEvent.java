package com.io.github.pedroolivsz.cardapio.event;

import com.io.github.pedroolivsz.cardapio.order.dto.OrderResponse;

/**
 * Evento publicado após a criação de um pedido, consumido
 * após o commit da transação para disparar notificações.
 *
 * @param orderResponse dados do pedido criado
 */
public record OrderCreatedEvent(OrderResponse orderResponse) {}