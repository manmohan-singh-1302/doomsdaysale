package com.doomsdaysale.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne // user can have only one wishlist
    private User user;

    @ManyToMany // a product can be in many wishlist and a wishlist can contain many products.
    private Set<Product> products = new HashSet<>();
}
