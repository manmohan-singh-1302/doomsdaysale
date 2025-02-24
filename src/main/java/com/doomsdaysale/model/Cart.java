package com.doomsdaysale.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne // One user can have only one cart
    private User user;

    // This will store all the unique items in the cart and its occurrences
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true) // one cart can have many cart items.
    // This is mapped to cart entity and the cascade property will automatically apply the changes to the cart when it is made on cartItems.
    // The orphan removal property ensures that when an entity is removed from relationship it is removed from the database ensuring integrity.
    private Set<CartItem> cartItems = new HashSet<>();

    private double totalSellingPrice;

    private int totalItem;

    private int totalMrpPrice;

    private int discount;

    private String couponCode;
}
