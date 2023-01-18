package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.repositories.CardRepository;
import com.example.demo.entities.Card;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserRepository userRepository;


  /*  public CreditCard withdraw(int amount, String iban) {

        CreditCard card = cardRepository.findCreditCardByIBAN(iban);
                card.setSoldCard(card.getSoldCard() - amount);
                cardRepository.save(card);
                return card;
    }*/
    //deposit
    //checking balance

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<Card> getAllCreditCards(){
        return cardRepository.findAll();
    }

}
