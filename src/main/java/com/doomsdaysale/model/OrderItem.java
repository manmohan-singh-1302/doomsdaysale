package com.doomsdaysale.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItem {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne // One order has multiple order items.
    private Order order;

    @ManyToOne
    private Product product;

    private String size;

    private int quantity;

    private Integer mrpPrice;

    private Integer sellingPrice;

    private Long userId;
}
