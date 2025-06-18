package com.doomsdaysale.repository;

import com.doomsdaysale.domain.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import com.doomsdaysale.model.Seller;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus status);
}
