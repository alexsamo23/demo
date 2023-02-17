package com.example.demo.services;

import com.example.demo.entities.Card;
import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.CardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.lang.module.ResolutionException;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

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

       //then
       List<Card> result= cardService.getAllCreditCards();
       assertEquals(2,result.size());

    }

    @Test
    void CardShouldBeReturnedByName() {
        //given
        Card card1 = new Card();
        card1.setName("card");
        //when
        Mockito.when(cardRepository.findCardByName("card")).thenReturn(card1);

        //then
        Card card = cardService.getCardByName(card1.getName());
        assertEquals(card1,card);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cardService.getCardByName("name");
        });
        String expectedMessage = "Card not found with name : 'name'";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
//
    }

    @Test
    void BalanceShouldbereturnedCorrectly() {
        //given
        Card card1 = new Card();
        card1.setId(1);
        card1.setSold(300);
        //when
        Mockito.when(cardRepository.findCardById(1L)).thenReturn(card1);

        //then
        int sold = cardService.checkBalance(card1.getId());
        assertEquals(300,sold);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cardService.checkBalance(2L);
        });
        String expectedMessage = "Card not found with id : '2'";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    void DepositShouldBeDoneCorrectly() throws InvalidWithdrawException {
        //given
        Card card1 = new Card();
        card1.setId(1);
        card1.setSold(300);
        //when
        Mockito.when(cardRepository.findCardById(1L)).thenReturn(card1);
        //then
        Card card = cardService.deposit(card1.getId(),500);
        assertEquals(800,card.getSold());

    }

    @Test
    void WithdrawShouldBeDoneCorrectly() throws InvalidWithdrawException {
        //given
        Card card1 = new Card();
        card1.setId(1);
        card1.setSold(500);
        //when
        Mockito.when(cardRepository.findCardById(1L)).thenReturn(card1);
        //then
        Card card = cardService.withdraw(card1.getId(),200);
        assertEquals(300,card.getSold());

        Exception exception = assertThrows(InvalidWithdrawException.class, () -> {
            cardService.withdraw(1L,600);
        });
        String expectedMessage = "Fonduri insuficiente";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void TestIfLimitIsChanged() {
        //given
        Card card1 = new Card();
        card1.setId(1);
        card1.setDailyLimit(500);
        //when
        Mockito.when(cardRepository.findCardById(1L)).thenReturn(card1);
        //then
        Card card = cardService.changeLimit(card1.getId(),200);
        assertEquals(200,card.getDailyLimit());
    }

    @Test
    void TestStatusIsChanged() {
        //given
        Card card1 = new Card();
        card1.setId(1);
        card1.setStatus(false);
        //when
        Mockito.when(cardRepository.findCardById(1L)).thenReturn(card1);
        //then
        Card card = cardService.changeStatus(card1.getId(),true);
        assertTrue(card.isStatus());
    }

    @Test
    void VerifyCardIsSaved() {
        //given
        Card card1 = new Card();
        //when
        Mockito.when(cardRepository.save(card1)).thenReturn(card1);
        //then
        Card card = cardService.saveCard(card1);
        assertEquals(card1,card);
    }

    @Test
    void VerifyCardIsUpdated() {
        //given
        Card card1 = new Card();
        card1.setId(1);

        Card card2 = new Card();
        card2.setId(2);
        card2.setName("card2");
        card2.setNumber("1234");
        card2.setCvv("234");
        card2.setExpireDate("06/23");
        card2.setSold(600);
        card2.setIBAN("RO2435");
        card2.setPIN("1234");
        card2.setDailyLimit(1000);
        card2.setStatus(true);
        //when
        Mockito.when(cardRepository.findCardById(1L)).thenReturn(card1);
        //then
        Card card = cardService.updateCard(card2,card1.getId());
        assertEquals("card2",card.getName());

    }

    @Test
    void VerifyCardIsDeleted() {
        //given
        Card card1 = new Card();
        card1.setName("card");
        card1.setId(1);
        //when
        Mockito.when(cardRepository.findCardById(1L)).thenReturn(card1);
        //then
        String card = cardService.deleteCard(card1.getId());
        assertEquals("Card deleted",card);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            cardService.checkBalance(2L);
        });
        String expectedMessage = "Card not found with id : '2'";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));


    }
}