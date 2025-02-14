package com.doomsdaysale.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import jakarta.persistence.Id;
//import org.springframework.data.annotation.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String locality;

    private String address;

    private String city;

    private String state;

    private String pinCode;

    private String mobile;
}
