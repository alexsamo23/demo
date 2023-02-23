package com.example.demo.controllers;

import com.example.demo.entities.Card;
import com.example.demo.entities.User;

import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.security.SecurityUser;
import com.example.demo.services.ICardService;
import com.example.demo.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class CardController {

    private final ICardService cardService;
    private final IUserService userService;
    private final String page ="redirect:/userViewOwnCards";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public CardController(ICardService cardService, IUserService userService) {
        this.cardService = cardService;
        this.userService = userService;
    }

    @GetMapping("/addNewCard")
    @PreAuthorize("hasAuthority('write')")
    public String showAddCardForm(Card card,Model model) {
        model.addAttribute("card",card);
        model.addAttribute("users",userService.getAllUsers());

        return "add_card_form";
    }

    @PostMapping("/processAddNewCard")
    @PreAuthorize("hasAuthority('write')")
    public String processCard(Card card, String email) {
        //model.addAttribute("users",userService.getAllUsers());
        card.setUser(userService.getUserWithEmail(email));
        cardService.saveCard(card);

        return "redirect:/adminViewCards/all";
    }

    @GetMapping("/editCard/{id}")
    @PreAuthorize("hasAuthority('write')")
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
    public String deposit(@RequestParam("id") Long id, @RequestParam("amount") int amount ) throws InvalidWithdrawException {
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
    @PreAuthorize("hasAuthority('write')")
    public String getByKeyword(Model model, String keyword) {
        if(keyword!=null && keyword!="") {
            User user = userService.getUserWithEmail(keyword);
            List<Card> cards = cardService.getAllCreditCardsWithId(user.getId());
            model.addAttribute("cards",cards);
        }
        else {
            List<Card> list= cardService.getAllCreditCards();
            model.addAttribute("cards",list);
        }
        return "viewCards";
    }

    @GetMapping("/adminViewCardsAscending")
    @PreAuthorize("hasAuthority('write')")
    public String getCardsInAscendingOrder(Model model){
        model.addAttribute("cards", cardService.getCardsInAscendingOrder());

        return "viewCards";
    }
    @GetMapping("/adminViewCardsDescending")
    @PreAuthorize("hasAuthority('write')")
    public String getCardsInDescendingOrder(Model model){
        model.addAttribute("cards", cardService.getCardsInDescendingOrder());

        return "viewCards";
    }
    @GetMapping("/adminViewCardsActivated")
    @PreAuthorize("hasAuthority('write')")
    public String getAllActiveCards(Model model){
        model.addAttribute("cards", cardService.getAllActiveCards());

        return "viewCards";
    }

    @GetMapping("/adminViewCardsDeactivated")
    @PreAuthorize("hasAuthority('write')")
    public String getAllDeactivatedCards(Model model){
        model.addAttribute("cards", cardService.getAllDeactivatedCards());

        return "viewCards";
    }
    @GetMapping("/redirectToProperFilterEndpoint")
    @PreAuthorize("hasAuthority('write')")
    public String getAllDeactivatedCards(Model model,String filter_id){
    String returnedPage;
        switch(filter_id) {
            case "Ascending":
                returnedPage= "redirect:/adminViewCardsAscending";
                break;
            case "Descending":
                returnedPage= "redirect:/adminViewCardsDescending";
                break;
            case "Activated":
                returnedPage = "redirect:/adminViewCardsActivated";
                break;
            case "Deactivated":
                returnedPage = "redirect:/adminViewCardsDeactivated";
                break;
            default:
                returnedPage = "redirect:/adminViewCards/all";
        }
        return returnedPage;
    }

}
