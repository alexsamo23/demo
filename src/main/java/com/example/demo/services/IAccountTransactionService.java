package com.example.demo.services;

import com.example.demo.entities.AccountTransaction;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAccountTransactionService {
    List<AccountTransaction> getAllTransactions();
    List<AccountTransaction> getAllTransactionsWithId(Long id);
    Page<AccountTransaction> findPage(int pageNumber);
    Page<AccountTransaction> findPageWithId(Long id,int pageNumber);
}
