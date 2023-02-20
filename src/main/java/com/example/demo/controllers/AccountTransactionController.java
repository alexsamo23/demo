package com.example.demo.controllers;

import com.example.demo.entities.AccountTransaction;
import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import com.example.demo.services.IAccountTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AccountTransactionController {

    @Autowired
    private IAccountTransactionService accountTransactionService;

    @GetMapping("/history")
    @PreAuthorize("hasAuthority('write')")
    public String getAllTransactions(Model model){
        model.addAttribute("transactions", accountTransactionService.getAllTransactions());

        return "viewHistory";
    }
    @PostMapping("/searchTransactions")
    public String getByKeyword(Model model,Long id) {
        if(id!=null) {
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
