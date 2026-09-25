package com.io.github.pedroolivsz.cardapio.event;

import com.io.github.pedroolivsz.cardapio.evolutionApi.service.EvolutionApiService;
import com.io.github.pedroolivsz.cardapio.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Listener responsável por disparar efeitos colaterais
 * (notificações) após o commit de transações relacionadas a pedidos.
 */
@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final EvolutionApiService evolutionApiService;

    /**
     * Envia as notificações referentes a um novo pedido
     * (WebSocket e mensagens via Evolution API) após o
     * commit da transação que o criou.
     *
     * @param event evento contendo os dados do pedido criado
     */
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOrderCreated(OrderCreatedEvent event) {
        OrderResponse orderResponse = event.orderResponse();

        messagingTemplate.convertAndSend("/topic/orders", orderResponse);
        evolutionApiService.notifyEstablishment(orderResponse);
        evolutionApiService.notifyClient(orderResponse);
    }
}
