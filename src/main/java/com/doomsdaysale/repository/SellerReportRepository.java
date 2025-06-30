package com.doomsdaysale.repository;

import com.doomsdaysale.model.SellerReport;
import com.doomsdaysale.service.SellerReportService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {

    SellerReport findBySellerId(Long sellerId);
}
