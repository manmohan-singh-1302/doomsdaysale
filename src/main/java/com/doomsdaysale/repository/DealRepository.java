package com.doomsdaysale.repository;

import com.doomsdaysale.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Long> {
}
