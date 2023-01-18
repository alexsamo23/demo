package com.example.demo.CardRepository;

import com.example.demo.Entities.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card,Long> {

    public Card findCreditCardByIBAN(String IBAN);
}
