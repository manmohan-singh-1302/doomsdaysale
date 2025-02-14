package com.doomsdaysale.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String code;

    private double discountPercentage;

    private LocalDate validityStartDate;

    private LocalDate validityEndDate;

    private double minimumOrderValue;

    private boolean isActive = true;

    @ManyToMany(mappedBy = "usedCoupons")// by providing the mappedBy value we are giving the property which holds the relationship and helps
    // in bypassing the creation of a redundant join table (usedByUsers_usedCoupons). This is used by the other entity in the relationship or inverse entity
    private Set<User> usedByUsers = new HashSet<>();
}
