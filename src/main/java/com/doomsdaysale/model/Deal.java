package com.doomsdaysale.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Integer discount;

    @OneToOne // this represent the category on which the discount is offered.
    private HomeCategory category;
}
