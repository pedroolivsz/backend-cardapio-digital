package com.io.github.pedroolivsz.cardapio.order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.io.github.pedroolivsz.cardapio.orderItem.entity.OrderItem;
import com.io.github.pedroolivsz.cardapio.order.enums.OrderStatus;
import com.io.github.pedroolivsz.cardapio.order.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String customerName;
    private String customerPhone;
    private String address;
    @Column(length = 500)
    private String observation;
    @Enumerated(value = EnumType.STRING)
    private PaymentMethod paymentMethod;
    private BigDecimal changeFor;
    private LocalDateTime createdAt;
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;
    private BigDecimal total;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<OrderItem> items;

    public Order(String customerName,
                 String customerPhone,
                 String address,
                 LocalDateTime createdAt,
                 OrderStatus status
                 ) {
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.address = address;
        this.createdAt = createdAt;
        this.status = status;
    }
}
