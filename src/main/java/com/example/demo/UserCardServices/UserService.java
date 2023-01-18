package com.example.demo.UserCardServices;

import com.example.demo.CardRepository.CardRepository;
import com.example.demo.Entities.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private CardRepository cardRepository;

  /*  public CreditCard withdraw(int amount, String iban) {

        CreditCard card = cardRepository.findCreditCardByIBAN(iban);
                card.setSoldCard(card.getSoldCard() - amount);
                cardRepository.save(card);
                return card;
    }*/

    public List<Card> getAllCreditCards(){
        return cardRepository.findAll();
    }

}
