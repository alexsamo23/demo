package com.example.demo.services;
import com.example.demo.entities.AccountTransaction;
import com.example.demo.entities.AccountUtils;
import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CardRepository;
import com.example.demo.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements ICardService {


   private final CardRepository cardRepository;
   private final TransactionsRepository transactionsRepository;
   @Autowired
    public CardServiceImpl(CardRepository cardRepository,TransactionsRepository transactionsRepository) {
     this.cardRepository= cardRepository;
     this.transactionsRepository= transactionsRepository;
    }

    enum Operation{
    Deposit,
    Withdraw,
    ChangeLimit
    }

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
    public Card deposit(Long id, int amount) throws InvalidWithdrawException {
        int total =0;
        String type = String.valueOf(Operation.Deposit);
        Card card = cardRepository.findCardById(id);
        if(card.isStatus()) {

            List<AccountTransaction> deposits = transactionsRepository.findByDateBetweenAndTypeAndCardId(AccountUtils.getStartOfDay(new Date()),
                    AccountUtils.getEndOfDay(new Date()), type, id);

            for (AccountTransaction accountTransaction : deposits) {
                total += accountTransaction.getAmount();
            }

            if ((total + amount) > card.getDailyLimit()) {

                throw new InvalidWithdrawException("Limita maxima a fost atinsa");

            } else {
                AccountTransaction accountTransaction = new AccountTransaction();
                accountTransaction.setAmount(amount);
                accountTransaction.setDate(new Date());
                accountTransaction.setCard(card);
                accountTransaction.setType(type);
                transactionsRepository.save(accountTransaction);

                card.setSold(card.getSold() + amount);
                cardRepository.save(card);
            }
        }
        else throw new InvalidWithdrawException(" Card blocat. Activati cardul inainte de a repeta operatia.");

        return card;
    }

    @Override
    public Card withdraw(Long id, int amount) throws InvalidWithdrawException {
        int total =0;
        String type = String.valueOf(Operation.Withdraw);
        Card card = cardRepository.findCardById(id);
        if(card.isStatus()) {

            if (card.getSold() >= amount) {

                List<AccountTransaction> withdrawals = transactionsRepository.findByDateBetweenAndTypeAndCardId(AccountUtils.getStartOfDay(new Date()),
                        AccountUtils.getEndOfDay(new Date()),type ,id);

                for (AccountTransaction accountTransaction : withdrawals) {
                    total += accountTransaction.getAmount();
                }

                if ((total + amount) > card.getDailyLimit()) {

                    throw new InvalidWithdrawException("Limita maxima a fost atinsa");

                } else {
                    AccountTransaction accountTransaction = new AccountTransaction();
                    accountTransaction.setAmount(amount);
                    accountTransaction.setDate(new Date());
                    accountTransaction.setCard(card);
                    accountTransaction.setType(type);
                    transactionsRepository.save(accountTransaction);

                    card.setSold(card.getSold() - amount);
                    cardRepository.save(card);
                }

                return card;
            } else {
                throw new InvalidWithdrawException("Fonduri insuficiente");
            }
        }
        else throw new InvalidWithdrawException("Card blocat. Activati cardul inainte de a repeta operatia.");
    }


    @Override
    public Card changeLimit(Long id, int limit) {
        String type = String.valueOf(Operation.ChangeLimit);
        Card card = cardRepository.findCardById(id);

        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.setAmount(limit);
        accountTransaction.setDate(new Date());
        accountTransaction.setCard(card);
        accountTransaction.setType(type);
        transactionsRepository.save(accountTransaction);

        card.setDailyLimit(limit);
        cardRepository.save(card);

        return card;
    }

    @Override
    public Card changeStatus(Long id, boolean status) {
        Card card = cardRepository.findCardById(id);
        card.setStatus(status);
        cardRepository.save(card);

        return card;
    }

    @Override
    public Card saveCard(Card card){
        return cardRepository.save(card);
    }
    @Override
    public Card updateCard(Card card, Long id){
        Card existingCard = cardRepository.findCardById(id);
        existingCard.setId(id);
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
    public String deleteCard(Long id) {
        Optional<Card> card = Optional.ofNullable(cardRepository.findCardById(id));
        if(card.isPresent()){
            cardRepository.delete(card.get());
            return "Card deleted";
        }
        else {
            throw new ResourceNotFoundException("Card", "id", id);
        }
    }

    @Override
    public List<Card> getCardsInAscendingOrder(){
        return cardRepository.findByOrderByNameAsc();
    }
    @Override
    public List<Card> getCardsInDescendingOrder(){
        return cardRepository.findByOrderByNameDesc();
    }

    @Override
    public List<Card> getAllActiveCards(){
        return cardRepository.findAllActiveCards();
    }

    @Override
    public List<Card> getAllDeactivatedCards(){
        return cardRepository.findAllDeactivatedCards();
    }


}
