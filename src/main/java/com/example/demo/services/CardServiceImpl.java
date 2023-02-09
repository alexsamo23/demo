package com.example.demo.services;
import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements ICardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public List<Card> getAllCreditCards(){
        return cardRepository.findAll();
    }

    public Card getCardById(Long id){
        return cardRepository.findCardById(id);
    }
    @Override
    public List<Card> getAllCreditCardsWithId(Long id){
        return cardRepository.findCardByUserId(id);
    }

    @Override
    public Card getCardByName(String name) {
        Optional<Card> card = Optional.ofNullable(cardRepository.findCardByName(name));
        if (card.isPresent()) {
            return card.get();
        } else {
            throw new ResourceNotFoundException("Card", "name", name);
        }
    }

    @Override
    public int checkBalance(Long id) throws ResourceNotFoundException {
        Optional<Card> card = Optional.ofNullable(cardRepository.findCardById(id));
        if (card.isPresent()) {
             return card.get().getSold();
        } else {
            throw new ResourceNotFoundException("Card", "id", id);
        }
    }

    @Override
    public Card deposit(Long id, int amount) {
        Card card = cardRepository.findCardById(id);
        card.setSold(card.getSold()+amount );
        cardRepository.save(card);

        return card;

    }

    @Override
    public Card withdraw(Long id, int amount) throws InvalidWithdrawException {
        Card card = cardRepository.findCardById(id);
        if(card.getSold()>amount) {
            card.setSold(card.getSold() - amount);
            cardRepository.save(card);

            return card;
        }
        else {
             throw new InvalidWithdrawException("Fonduri insuficiente");

        }

    }

    @Override
    public Card changeLimit(String name, int limit) {
        Card card = cardRepository.findCardByName(name);
        card.setDailyLimit(limit);
        cardRepository.save(card);

        return card;

    }

    @Override
    public Card changeStatus(String name, boolean status) {
        Card card = cardRepository.findCardByName(name);
        card.setStatus(status);
        cardRepository.save(card);

        return card;

    }

    @Override
    public Card saveCard(Card card){
        return cardRepository.save(card);
    }
    @Override
    public Card updateCard(Card card, String name){
        Card existingCard =cardRepository.findCardByName(name);
        existingCard.setId(card.getId());
        existingCard.setName(card.getName());
        existingCard.setNumber(card.getNumber());
        existingCard.setCvv(card.getCvv());
        existingCard.setExpireDate(card.getExpireDate());
        existingCard.setSold(card.getSold());
        existingCard.setIBAN(card.getIBAN());
        existingCard.setPIN(card.getPIN());
        existingCard.setDailyLimit(card.getDailyLimit());
        existingCard.setStatus(card.isStatus());
        cardRepository.save(existingCard);

        return existingCard;

    }

    @Override
    public String deleteCard(String name) {
        Optional<Card> card = Optional.ofNullable(cardRepository.findCardByName(name));
        if(card.isPresent()){
            cardRepository.delete(card.get());
            return "Card deleted";
        }
        else {
            throw new ResourceNotFoundException("Card", "name", name);
        }
    }

}
