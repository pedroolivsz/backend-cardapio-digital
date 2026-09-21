package com.io.github.pedroolivsz.cardapio.evolutionApi.service;

import com.io.github.pedroolivsz.cardapio.orderItem.dto.OrderItemResponse;
import com.io.github.pedroolivsz.cardapio.order.dto.OrderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class EvolutionApiService {
    private final RestTemplate restTemplate;

    @Value("${evolution.api.url}")
    private String apiUrl;
    @Value("${evolution.api.key}")
    private String apiKey;
    @Value("${evolution.api.instance}")
    private String instance;
    @Value("${evolution.establishment.phone}")
    private String establishmentPhone;

    public EvolutionApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendMessage(String phone, String message) {
        String url = apiUrl + "/message/sendText/" + instance;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", apiKey);

        String formattedPhone = formatPhone(phone);

        Map<String, Object> body = Map.of(
            "number", formattedPhone,
            "text", message
        );

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            restTemplate.postForEntity(url, request, String.class);
        } catch (Exception e) {
            System.err.println("Erro ao enviar WhastsApp para " + phone + ": " + e.getMessage());
        }
    }

    public void notifyEstablishment(OrderResponse orderResponse) {
        String message = buildEstablishmentMessage(orderResponse);
        sendMessage(establishmentPhone, message);
    }

    public void notifyClient(OrderResponse orderResponse) {
        if(orderResponse.customerPhone() == null || orderResponse.customerPhone().isBlank()) return;
        String message = buildClientMessage(orderResponse);
        sendMessage(orderResponse.customerPhone(), message);
    }

    private String buildEstablishmentMessage(OrderResponse orderResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("*Novo Pedido #").append(orderResponse.id()).append("*\n\n");
        stringBuilder.append("*Cliente:*").append(orderResponse.customerName()).append("\n");
        stringBuilder.append("*Telefone:*").append(orderResponse.customerPhone()).append("\n");

        if(orderResponse.address() != null && !orderResponse.address().isEmpty()) {
            stringBuilder.append("*Endereço:*").append(orderResponse.address()).append("\n");
        }

        stringBuilder.append("\n*Itens:*\n");
        orderResponse.items().forEach(item ->
                stringBuilder.append("  • ").append(item.quantity()).append("x ")
                        .append(item.foodName())
                        .append(" - R$ ").append(item.price()).append("\n")
        );

        BigDecimal total = BigDecimal.ZERO;
        for(OrderItemResponse itemResponse: orderResponse.items()) {
            total = total.add(itemResponse.price().multiply(BigDecimal.valueOf(itemResponse.quantity())));
        }

        stringBuilder.append("\n*Total:* R$").append(total).append("\n");
        stringBuilder.append("*Pagamento:* ").append(formatPaymentMethod(orderResponse.paymentMethod())).append("\n");

        if (orderResponse.changeFor() != null) {
            stringBuilder.append("*Troco para:* R$ ").append(orderResponse.changeFor()).append("\n");
        }

        if (orderResponse.observation() != null && !orderResponse.observation().isBlank()) {
            stringBuilder.append("\n*Obs:* ").append(orderResponse.observation()).append("\n");
        }

        return stringBuilder.toString();
    }

    private String buildClientMessage(OrderResponse orderResponse) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("*Pedido confirmado!*\n\n");
        stringBuilder.append("Olá, ").append(orderResponse.customerName()).append("! Recebemos seu pedido.\n\n");
        stringBuilder.append("*Resumo:*\n");
        orderResponse.items().forEach(item ->
                stringBuilder.append("  • ").append(item.quantity()).append("x ")
                        .append(item.foodName()).append("\n")
        );
        stringBuilder.append("\n*Total:* R$ ").append(orderResponse.total()).append("\n");
        stringBuilder.append("*Pagamento:* ").append(formatPaymentMethod(orderResponse.paymentMethod())).append("\n\n");
        stringBuilder.append("Seu pedido está sendo preparado. Avisaremos quando sair para entrega!");

        return stringBuilder.toString();
    }

    private String formatPaymentMethod(String method) {
        return switch (method) {
            case "CASH" -> "Dinheiro";
            case "CREDIT_CARD" -> "Cartão de Crédito";
            case "DEBIT_CARD" -> "Cartão de Débito";
            case "PIX" -> "Pix";
            default -> method;
        };
    }

    private String formatPhone(String phone) {
        String digits = phone.replaceAll("\\D", "");

        if(!digits.startsWith("55")) {
            digits = "55" + digits;
        }
        return digits;
    }
}
