package com.doomsdaysale.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String otp;

    private String email;

    @OneToOne // user can have one verification code
    private User user;

    @OneToOne // seller can have one verification code
    private Seller seller;
}
