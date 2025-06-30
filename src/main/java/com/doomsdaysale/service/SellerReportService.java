package com.doomsdaysale.service;

import com.doomsdaysale.model.Seller;
import com.doomsdaysale.model.SellerReport;

public interface SellerReportService {

    SellerReport getSellerReport(Seller seller);

    SellerReport updateSellerReport(SellerReport sellerReport);
}
