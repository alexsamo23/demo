package com.example.demo.controllers;

import com.example.demo.entities.Card;
import com.example.demo.services.CardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes ={ CardController.class})
@WebMvcTest(value = CardController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class CardControllerTest {

    @MockBean
    private CardServiceImpl cardService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void verifyShowAddCardForm() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/addNewCard"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add_card_form"));
                //.andExpect(MockMvcResultMatchers.model().attribute("card",card));
    }

    @Test
    void verifyProcessCard() throws Exception {
        Card card = new Card();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/processAddNewCard").sessionAttr("card", card))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/adminViewCards/all"));
    }

    @Test
    void verifyEditCardForm() throws Exception {
        Card card = new Card();
        card.setId(1);
        Mockito.when(cardService.getCardById(1L)).thenReturn(card);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/editCard/{id}",1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("edit_card"))
                .andExpect(MockMvcResultMatchers.model().attribute("card",card));
    }

    @Test
    void verifyUpdateCard() throws Exception {
        Card card = new Card();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/updateCard/{id}", 1L).sessionAttr("card", card))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/adminViewCards/all"));
    }

    @Test
    void verifyCheckBalance() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/userCheck").param("id","1")).andDo(print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/userViewOwnCards"));


    }

    @Test
    void verifyDeposit() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/deposit").param("id","1").param("amount","100"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/userViewOwnCards"));
    }

    @Test
    void verifyWithdraw() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/withdraw").param("id","1").param("amount","100"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/userViewOwnCards"));
    }

    @Test
    void verifyChangeLimit() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/userOp/limit").param("id","1").param("limit","1000"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/userViewOwnCards"));
    }

    @Test
    void verifyChangeStatus() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/userOp/status").param("id","1").param("status","true"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/userViewOwnCards"));
    }

    @Test
    void verifyDeleteCard() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/deleteCard/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/adminViewCards/all"));
    }

    @Test
    void verifyGetAllCreditCards() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/adminViewCards/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("viewCards"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("cards"));

    }

    @Test
    void editOwnCreditCards() {
    }

    @Test
    void handleInvalidWithdrawException() {
    }

    @Test
    void handleResourceNotFoundException() {
    }
}