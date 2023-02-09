package com.example.demo.controllers;

import com.example.demo.entities.Card;
import com.example.demo.exceptions.ErrorResponse;
import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.ICardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
//@RequestMapping("/card")
public class CardController {
    @Autowired
    private ICardService cardService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("/userCheck")
    public String checkBalance(@RequestParam("id") Long id, Model model) throws ResourceNotFoundException {

        model.addAttribute("sold",cardService.checkBalance(id));

        return "register_success";

    }

    @PostMapping("/deposit")
    public String deposit(@RequestParam("id") Long id, @RequestParam("amount") int amount ){

        cardService.deposit(id,amount);
        logger.info("Deposit successfully");

        return "register_success";
    }

    @PostMapping("/withdraw")
    public String withdraw (@RequestParam("id") Long id, @RequestParam("amount") int amount ) throws InvalidWithdrawException {

        cardService.withdraw(id,amount);
        logger.info("Withdraw successfully");

        return "register_success";
    }



    @PutMapping("/userOp/{name}/limit/{limit}")
    public ResponseEntity <Card> changeLimit(@PathVariable("name") String name,@PathVariable("limit") int limit)  {

        return new ResponseEntity<Card>(cardService.changeLimit(name,limit), HttpStatus.OK);
    }
    @PutMapping("/userOp/{name}/status/{status}")
    public ResponseEntity <Card> changeStatus(@PathVariable("name") String name,@PathVariable("status") boolean status)  {

        return new ResponseEntity<Card>(cardService.changeStatus(name,status), HttpStatus.OK);
    }

    @PostMapping("/adminCard/save")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Card> saveCard (@RequestBody Card card){
        return new ResponseEntity<Card>(cardService.saveCard(card),HttpStatus.CREATED);
    }

    @PutMapping("/admin/updateCard/{name}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<Card> updateCard (@RequestBody Card card, @PathVariable("name") String name){

     return new ResponseEntity<Card>(cardService.updateCard(card, name),HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{name}")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<String> deleteCard(@PathVariable("name") String name){
        cardService.deleteCard(name);

        return new ResponseEntity<String>("Card deleted succesfully", HttpStatus.OK);
    }

    @GetMapping("/adminViewCards/all")
    @PreAuthorize("hasAuthority('write')")
    public String getAllCreditCards(Model model){
        model.addAttribute("cards", cardService.getAllCreditCards());
        return "viewAllCards";
    }

    @GetMapping("/userViewOwnCards/all/{id}")
    public String getOwnCreditCards(@PathVariable("id") Long id, Model model){
        model.addAttribute("cards", cardService.getAllCreditCardsWithId(id));
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
