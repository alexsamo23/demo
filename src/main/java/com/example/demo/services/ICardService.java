package com.example.demo.services;

import com.example.demo.entities.Card;
import com.example.demo.exceptions.InvalidWithdrawException;

import java.util.List;

public interface ICardService {

    List<Card> getAllCreditCards();
    Card getCardByName(String name);
    int checkBalance(String name);
    Card deposit(String name, int amount);
    Card withdraw(String name, int amount) throws InvalidWithdrawException;
    Card changeLimit(String name, int limit);
    Card changeStatus(String name, boolean status);
    Card saveCard(Card card);
    Card updateCard(Card card, String name);
    String deleteCard(String name);
}
