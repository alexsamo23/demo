package com.example.demo.controllers;

import com.example.demo.entities.AccountTransaction;
import com.example.demo.security.SecurityUser;
import com.example.demo.services.IAccountTransactionService;
import com.example.demo.services.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

@Controller
public class AccountTransactionController {

    @Autowired
    private IAccountTransactionService accountTransactionService;
    @Autowired
    private ICardService cardService;
    @GetMapping("/history")
    @PreAuthorize("hasAuthority('write')")
    public String getAllTransactions(Model model){
        model.addAttribute("transactions", accountTransactionService.getAllTransactions());

        return "viewHistory";
    }
    @GetMapping("/ownHistory")
    public String getOwnCards(Model model){
        SecurityUser userCustom = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId= userCustom.getUser().getId();
        model.addAttribute("cards",cardService.getAllCreditCardsWithId(userId));

        return "view_own_cards_history";
    }

    @GetMapping("/ownCardHistory/{id}")
    public String getOwnTransactions(Model model, @PathVariable("id") Long id){

        model.addAttribute("transactions",accountTransactionService.getAllTransactionsWithId(id));

        return "viewHistory";
    }

    @PostMapping("/searchTransactions")
    @PreAuthorize("hasAuthority('write')")
    public String getTransactionsById(Model model,Long id) {
        if(id != null) {
            List<AccountTransaction> transactions = accountTransactionService.getAllTransactionsWithId(id);
            model.addAttribute("transactions", transactions);
        }
        else {
            List<AccountTransaction> list= accountTransactionService.getAllTransactions();
            model.addAttribute("cards",list);
        }
        return "viewHistory";
    }

}
