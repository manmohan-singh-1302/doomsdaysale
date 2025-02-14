package com.doomsdaysale.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @NotNull // This will ensure that the attribute is not null.
    @Column(unique = true) // This will ensure that the attribute contains only unique values.
    private String categoryId;

    @ManyToOne
    private Category parentCategory;

    @NotNull
    private Integer level;

}
