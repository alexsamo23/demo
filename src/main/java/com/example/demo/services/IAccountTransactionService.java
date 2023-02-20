package com.example.demo.services;

import com.example.demo.entities.AccountTransaction;
import java.util.List;

public interface IAccountTransactionService {
    List<AccountTransaction> getAllTransactions();
    List<AccountTransaction> getAllTransactionsWithId(Long id);
}
