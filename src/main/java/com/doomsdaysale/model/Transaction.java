package com.doomsdaysale.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne // a user can make multiple transactions
    private User customer;

    @OneToOne //
    private Order order;

    @ManyToOne // one seller can have many transactions
    private Seller seller;

    private LocalDateTime date = LocalDateTime.now();
}
