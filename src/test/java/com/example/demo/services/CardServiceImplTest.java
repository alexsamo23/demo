package com.example.demo.services;

import com.example.demo.entities.Card;
import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    @Test
    void AllCreditCardsShouldBeReturned() {
        //given
        Card card1 = new Card();
        Card card2 = new Card();
        List<Card> input = new ArrayList<Card>();
        input.add(card1);
        input.add(card2);

        //when
       Mockito.when(cardRepository.findAll()).thenReturn(input);

        List<Card> result= cardService.getAllCreditCards();

        assertEquals(2,result.size());

    }

    @Test
    void CardShouldBeReturnedByName() {
        Card card1 = new Card();
        card1.setName("card");

        Mockito.when(cardRepository.findCardByName("card")).thenReturn(card1);

        Card card = cardService.getCardByName(card1.getName());

        assertEquals(card1,card);

    }

    @Test
    void BalanceShouldbereturnedCorrectly() {
        Card card1 = new Card();
        card1.setName("card");
        card1.setSold(300);

        Mockito.when(cardRepository.findCardByName("card")).thenReturn(card1);

        int sold = cardService.checkBalance(card1.getId());
        assertEquals(300,sold);
    }

    @Test
    void DepositShouldBeDoneCorrectly() {
        Card card1 = new Card();
        card1.setName("card");
        card1.setSold(300);

        Mockito.when(cardRepository.findCardByName("card")).thenReturn(card1);

        Card card = cardService.deposit(card1.getId(),500);

        assertEquals(800,card.getSold());

    }

    @Test
    void WithdrawShouldBeDoneCorrectly() throws InvalidWithdrawException {
        Card card1 = new Card();
        card1.setName("card");
        card1.setSold(500);

        Mockito.when(cardRepository.findCardByName("card")).thenReturn(card1);

        Card card = cardService.withdraw(card1.getId(),200);

        assertEquals(300,card.getSold());
    }

    @Test
    void TestIfLimitIsChanged() {
        Card card1 = new Card();
        card1.setName("card");
        card1.setDailyLimit(500);

        Mockito.when(cardRepository.findCardByName("card")).thenReturn(card1);

        Card card = cardService.changeLimit(card1.getName(),200);

        assertEquals(200,card.getDailyLimit());
    }

    @Test
    void TestStatusIsChanged() {
        Card card1 = new Card();
        card1.setName("card");
        card1.setStatus(false);

        Mockito.when(cardRepository.findCardByName("card")).thenReturn(card1);

        Card card = cardService.changeStatus(card1.getName(),true);

        assertEquals(true,card.isStatus());
    }

    @Test
    void VerifyCardIsSaved() {
        Card card1 = new Card();

        Mockito.when(cardRepository.save(card1)).thenReturn(card1);

        Card card = cardService.saveCard(card1);
        assertEquals(card1,card);
    }

    @Test
    void VerifyCardIsUpdated() {
        Card card1 = new Card();
        card1.setName("card");

        Card card2 = new Card();
        card2.setId(1);
        card2.setName("card2");
        card2.setNumber("1234");
        card2.setCvv("234");
        card2.setExpireDate("06/23");
        card2.setSold(600);
        card2.setIBAN("RO2435");
        card2.setPIN("1234");
        card2.setDailyLimit(1000);
        card2.setStatus(true);

        Mockito.when(cardRepository.findCardByName("card")).thenReturn(card1);

        Card card = cardService.updateCard(card2,"card");

        assertEquals("card2",card.getName());

    }

    @Test
    void VerifyCardIsDeleted() {
        Card card1 = new Card();
        card1.setName("card");

        Mockito.when(cardRepository.findCardByName("card")).thenReturn(card1);

        String card = cardService.deleteCard("card");

        assertEquals("Card deleted",card);

    }
}