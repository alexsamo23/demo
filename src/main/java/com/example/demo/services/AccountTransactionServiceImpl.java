package com.example.demo.services;

import com.example.demo.entities.AccountTransaction;
import com.example.demo.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AccountTransactionServiceImpl implements IAccountTransactionService{

    @Autowired
    private TransactionsRepository transactionsRepository;
    @Override
    public List<AccountTransaction> getAllTransactions() {
        return transactionsRepository.findAll();
    }
    @Override
    public List<AccountTransaction> getAllTransactionsWithId(Long id){
        return transactionsRepository.findAccountTransactionByCardId(id);
    }
}
