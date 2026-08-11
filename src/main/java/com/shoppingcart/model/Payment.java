package com.shoppingcart.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

import com.shoppingcart.enumerated.PaymentMethod;
import com.shoppingcart.enumerated.PaymentStatus;

@Entity
@Table(name = "payment")
@Getter
@Setter
@NoArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(length = 150)
    private String transactionId;

    private LocalDateTime paymentDate;
}