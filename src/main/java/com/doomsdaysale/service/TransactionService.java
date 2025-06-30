package com.doomsdaysale.service;

import com.doomsdaysale.model.Order;
import com.doomsdaysale.model.Seller;
import com.doomsdaysale.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Order order);
    List<Transaction> getTransactionsBySellerId(Seller seller);
    List<Transaction> getAllTransactions();
}
