package com.example.demo.controllers;

import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ErrorResponse;
import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.security.SecurityUser;
import com.example.demo.services.ICardService;
import com.example.demo.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller

public class CardController {
    @Autowired
    private ICardService cardService;
    @Autowired
    private IUserService userService;
    private final String page ="redirect:/userViewOwnCards";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/addNewCard")
    public String showAddCardForm(Card card,Model model) {
        model.addAttribute("card",card);

        return "add_card_form";
    }

    @PostMapping("/processAddNewCard")
    public String processCard(Card card) {
        cardService.saveCard(card);

        return "redirect:/adminViewCards/all";
    }

    @GetMapping("/editCard/{id}")
    public String editCardForm (@PathVariable("id") Long id, Model model) {
        model.addAttribute("card", cardService.getCardById(id));

        return "edit_card";
    }

    @PostMapping("/admin/updateCard/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String updateCard(@PathVariable ("id") Long id,@ModelAttribute("card") Card card) {
        cardService.updateCard(card, id);
        logger.info("User updated successfully");

        return "redirect:/adminViewCards/all";
    }

    @PostMapping("/userCheck")
    public String checkBalance(@RequestParam("id") Long id, Model model) throws ResourceNotFoundException {
        model.addAttribute("sold",cardService.checkBalance(id));

        return page;
    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam("id") Long id, @RequestParam("amount") int amount ){
        cardService.deposit(id,amount);
        logger.info("Deposit successfully");

        return page;
    }

    @PostMapping("/withdraw")
    public String withdraw (@RequestParam("id") Long id, @RequestParam("amount") int amount ) throws InvalidWithdrawException {
        cardService.withdraw(id,amount);
        logger.info("Withdraw successfully");

        return page;
    }

    @PostMapping("/userOp/limit")
    public String changeLimit(@RequestParam("id") Long id,@RequestParam("limit") int limit)  {
        cardService.changeLimit(id,limit);

        return "register_success";
    }
    @PostMapping("/userOp/status")
    public String changeStatus(@RequestParam("id") Long id,@RequestParam("status") boolean status)  {
        cardService.changeStatus(id,status);

        return "register_success";
    }


    @GetMapping("/admin/deleteCard/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String deleteCard(@PathVariable("id") Long id){
        cardService.deleteCard(id);

        return "redirect:/adminViewCards/all";
    }

    @GetMapping("/adminViewCards/all")
    @PreAuthorize("hasAuthority('write')")
    public String getAllCreditCards(Model model){
        model.addAttribute("cards", cardService.getAllCreditCards());

        return "viewCards";
    }

    @GetMapping("/userViewOwnCards")
    public String editOwnCreditCards (Model model) {
        SecurityUser userCustom = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId= userCustom.getUser().getId();
        model.addAttribute("cards",cardService.getAllCreditCardsWithId(userId));

        return "view_own_cards";
    }

    @PostMapping("/searchCards")
    public String getByKeyword(Model model, String keyword) {
        if(keyword!=null && keyword!="") {
            User user = userService.getUserWithEmail(keyword);
            List<Card> cards = cardService.getAllCreditCardsWithId(user.getId());
            model.addAttribute("cards",cards);

        }else {
            List<Card> list= cardService.getAllCreditCards();
            model.addAttribute("cards",list);

        }
        return "viewCards";
    }

   @ExceptionHandler(value = InvalidWithdrawException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse
    handleInvalidWithdrawException(
            InvalidWithdrawException ex)
    {
        return new ErrorResponse(HttpStatus.CONFLICT.value(),
                ex.getMessage());
    }
    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse
    handleResourceNotFoundException(
           ResourceNotFoundException ex)
    {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
    }

}
