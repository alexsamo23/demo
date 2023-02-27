package com.example.demo.controllers;

import com.example.demo.entities.AccountTransaction;
import com.example.demo.security.SecurityUser;
import com.example.demo.services.IAccountTransactionService;
import com.example.demo.services.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



import java.util.List;

@Controller
public class AccountTransactionController {

    @Autowired
    private IAccountTransactionService accountTransactionService;
    @Autowired
    private ICardService cardService;
    @GetMapping("/history1")
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

    @GetMapping("/ownCardHistory/{pageNumber}/{id}")

    public String getOnePageWithOwnId(Model model,@PathVariable("id") Long id, @PathVariable("pageNumber") int currentPage){
        if(id != null) {

            Page<AccountTransaction> page = accountTransactionService.findPageWithId(id,currentPage);
             int totalPages = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<AccountTransaction> accountTransactions = page.getContent();

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);//page.getTotalPages());
            model.addAttribute("totalItems",totalItems);// page.getSize());
            model.addAttribute("transactions",accountTransactions);// page);
            model.addAttribute("id",id);
        }

        else {

            getOnePage(model,currentPage);
        }
        return "viewOwnHistory";
    }
    @GetMapping("/searchTransactions")
    @PreAuthorize("hasAuthority('write')")
    public String getAllPagesWithId(Model model, Long id){
        return getOnePageWithId(model,id, 1);
    }

    @GetMapping("/searchTransactions/{pageNumber}/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String getOnePageWithId(Model model,@PathVariable("id") Long id, @PathVariable("pageNumber") int currentPage){
        if(id != null) {

            Page<AccountTransaction> page = accountTransactionService.findPageWithId(id,currentPage);
            int totalPages = page.getTotalPages();
            long totalItems = page.getTotalElements();
            List<AccountTransaction> accountTransactions = page.getContent();

            model.addAttribute("currentPage", currentPage);
            model.addAttribute("totalPages", totalPages);
            model.addAttribute("totalItems", totalItems);
            model.addAttribute("transactions",accountTransactions);
            model.addAttribute("id",id);
        }

        else {

           getOnePage(model,currentPage);
        }
        return "viewHistory";
    }
    @GetMapping("/history")
    @PreAuthorize("hasAuthority('write')")
    public String getAllPages(Model model){
        return getOnePage(model, 1);
    }

    @GetMapping("/history/{pageNumber}")
    @PreAuthorize("hasAuthority('write')")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage){
        Page<AccountTransaction> page = accountTransactionService.findPage(currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        List<AccountTransaction> accountTransactions = page.getContent();

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("transactions", accountTransactions);

        return "viewAllHistory";
    }

}
