package com.doomsdaysale.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.doomsdaysale.model.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
}
