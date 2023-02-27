package com.example.demo.repositories;



import com.example.demo.entities.AccountTransaction;
import com.example.demo.entities.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Repository
public interface TransactionsRepository extends JpaRepository <AccountTransaction, Long> {

    List<AccountTransaction> findByDateBetweenAndTypeAndCardId(Date StartOfDay, Date endOfDay,String type,Long id);
    //public List<AccountTransaction> findAccountTransactionByCardId(Long id);
    public Page<AccountTransaction> findAccountTransactionByCardId(Long id,Pageable pageable);
    //Page<AccountTransaction> findAllByNameContaining(String name, Pageable pageable);
}