package com.example.demo.services;

import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import com.example.demo.exceptions.InvalidWithdrawException;

import java.util.List;

public interface ICardService {

    List<Card> getAllCreditCards();
    List<Card> getAllCreditCardsWithId(Long id);
    Card getCardByName(String name);
    Card getCardById(Long id);
    int checkBalance(Long id);
    Card deposit(Long id, int amount) throws InvalidWithdrawException;
    Card withdraw(Long id, int amount) throws InvalidWithdrawException;
    Card changeLimit(Long id, int limit);
    Card changeStatus(Long id, boolean status);
    Card saveCard(Card card);
    Card updateCard(Card card, Long id);
    String deleteCard(Long id);
    List<Card> getCardsInAscendingOrder();
    List<Card> getCardsInDescendingOrder();
    List<Card> getAllActiveCards();
    List<Card> getAllDeactivatedCards();


}
