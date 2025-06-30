package com.doomsdaysale.service.impl;

import com.doomsdaysale.model.Order;
import com.doomsdaysale.model.Seller;
import com.doomsdaysale.model.Transaction;
import com.doomsdaysale.repository.SellerRepository;
import com.doomsdaysale.repository.TransactionRepository;
import com.doomsdaysale.service.SellerReportService;
import com.doomsdaysale.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final SellerRepository sellerRepository;

    @Override
    public Transaction createTransaction(Order order) {
        Seller seller = sellerRepository.findById(order.getSellerId()).get();

        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsBySellerId(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return List.of();
    }
}
