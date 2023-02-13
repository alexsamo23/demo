package com.example.demo.controllers;

import com.example.demo.entities.Card;
import com.example.demo.exceptions.ErrorResponse;
import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.security.SecurityUser;
import com.example.demo.services.ICardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
//@RequestMapping("/card")
public class CardController {
    @Autowired
    private ICardService cardService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/addNewCard")
    public String showAddCardForm(Card card,Model model) {
        model.addAttribute("card", card);

        return "add_card_form";
    }

    @PostMapping("/processAddNewCard")
    public String processCard(Card card) {

        cardService.saveCard(card);
        return "register_success";
    }

    @GetMapping("/editCard/{id}")
    public String editCardForm (@PathVariable("id") Long id, Model model) {

        model.addAttribute("card", cardService.getCardById(id));

        return "edit_card";
    }

    @PostMapping("/admin/updateCard/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String updateCard(@PathVariable ("id") Long id,@ModelAttribute("card") Card card, Model model) {

        cardService.updateCard(card, id);
        logger.info("User updated successfully");

        return "register_success";
    }

    @PostMapping("/userCheck")
    public String checkBalance(@RequestParam("id") Long id, Model model) throws ResourceNotFoundException {

        model.addAttribute("sold",cardService.checkBalance(id));
        Card card= cardService.getCardById(id);
        Long idUser= card.getUser().getId();

        return "redirect:/userViewOwnCards";

    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam("id") Long id, @RequestParam("amount") int amount ){

        cardService.deposit(id,amount);
        logger.info("Deposit successfully");
        Card card= cardService.getCardById(id);
        Long idUser= card.getUser().getId();


        return "redirect:/userViewOwnCards";
    }

    @PostMapping("/withdraw")
    public String withdraw (@RequestParam("id") Long id, @RequestParam("amount") int amount ) throws InvalidWithdrawException {

        cardService.withdraw(id,amount);
        logger.info("Withdraw successfully");
        Card card= cardService.getCardById(id);
        Long idUser= card.getUser().getId();


        return "redirect:/userViewOwnCards";
    }

    @PostMapping("/userOp/limit")
    public String changeLimit(@RequestParam("id") Long id,@RequestParam("limit") int limit)  {
        cardService.changeLimit(id,limit);

        Card card= cardService.getCardById(id);
        Long idUser= card.getUser().getId();

        return "redirect:/userViewOwnCards";
    }
    @PostMapping("/userOp/status")
    public String changeStatus(@RequestParam("id") Long id,@RequestParam("status") boolean status)  {

        cardService.changeStatus(id,status);
        Card card= cardService.getCardById(id);
        Long idUser= card.getUser().getId();

        return "redirect:/userViewOwnCards";
    }

    @PostMapping("/adminCard/save")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Card> saveCard (@RequestBody Card card){
        return new ResponseEntity<Card>(cardService.saveCard(card),HttpStatus.CREATED);
    }


    @GetMapping("/admin/deleteCard/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String deleteCard(@PathVariable("id") Long id){
        cardService.deleteCard(id);

        return "register_success";
    }

    @GetMapping("/adminViewCards/all")
    @PreAuthorize("hasAuthority('write')")
    public String getAllCreditCards(Model model){
        model.addAttribute("cards", cardService.getAllCreditCards());

        return "viewCards";
    }

    @GetMapping("/userViewOwnCards")
    //public String getOwnCreditCards(@PathVariable("id") Long id, Model model){
      //  model.addAttribute("cards", cardService.getAllCreditCardsWithId(id));

        public String editOwnCreditCards (Principal principal, Model model) {

            SecurityUser userCustom = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long userId= userCustom.getUser().getId();
            model.addAttribute("cards",cardService.getAllCreditCardsWithId(userId));

        return "view_own_cards";
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
