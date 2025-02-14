package com.doomsdaysale.model;

import com.doomsdaysale.domain.PaymentMethod;
import com.doomsdaysale.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long amount;

    private PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    private PaymentMethod paymentMethod; // which payment method is used

    private String paymentLinkId;

    @ManyToOne
    private User user;

    @OneToMany
    private Set<Order> orders = new HashSet<>();
}
