package com.example.demo.services;
import java.util.*;
import com.example.demo.entities.Card;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    public List<Card> getAllCreditCards(){
        return cardRepository.findAll();
    }

      /*  public CreditCard withdraw(int amount, String iban) {

        CreditCard card = cardRepository.findCreditCardByIBAN(iban);
                card.setSoldCard(card.getSoldCard() - amount);
                cardRepository.save(card);
                return card;
    }*/
    //deposit
    //checking balance

    public Card getCardByName(String name) {
        Optional<Card> card = Optional.ofNullable(cardRepository.findCardByName(name));
        if (card.isPresent()) {
            return card.get();
        } else {
            throw new ResourceNotFoundException("Card", "name", name);
        }
    }

    public int getSoldByName(String name) {
        Optional<Card> card = Optional.ofNullable(cardRepository.findCardByName(name));
        if (card.isPresent()) {
             return card.get().getSold();
        } else {
            throw new ResourceNotFoundException("Card", "name", name);
        }
    }
    }
