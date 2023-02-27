package com.example.demo.services;

import com.example.demo.entities.AccountTransaction;
import com.example.demo.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return null;//transactionsRepository.findAccountTransactionByCardId(id);
   }
    @Override
    public Page<AccountTransaction> findPage(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return transactionsRepository.findAll(pageable);
    }
    @Override
    public Page<AccountTransaction> findPageWithId(Long id,int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber - 1,5);
        return transactionsRepository.findAccountTransactionByCardId(id,pageable);
    }

}
