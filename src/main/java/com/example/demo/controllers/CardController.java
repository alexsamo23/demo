package com.example.demo.controllers;

import com.example.demo.entities.Card;
import com.example.demo.exceptions.ErrorResponse;
import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.exceptions.ResourceNotFoundException;

import com.example.demo.services.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/card")
public class CardController {
    @Autowired
    private ICardService cardService;

    @GetMapping("/userOp/{name}")
    public ResponseEntity<Integer> checkBalance(@PathVariable("name") String name) throws ResourceNotFoundException {

     return new ResponseEntity<Integer>(cardService.checkBalance(name), HttpStatus.OK);
    }

    @PutMapping("/userOp/{name}/deposit/{amount}")
    public ResponseEntity <Card> deposit(@PathVariable("name") String name,@PathVariable("amount") int amount) {

      return new ResponseEntity<Card>(cardService.deposit(name,amount), HttpStatus.OK);
    }

    @PutMapping("/userOp/{name}/withdraw/{amount}")
    public ResponseEntity <Card> withdraw(@PathVariable("name") String name,@PathVariable("amount") int amount) throws InvalidWithdrawException {

        return new ResponseEntity<Card>(cardService.withdraw(name, amount), HttpStatus.OK);
    }


    @PutMapping("/userOp/{name}/limit/{limit}")
    public ResponseEntity <Card> changeLimit(@PathVariable("name") String name,@PathVariable("limit") int limit)  {

        return new ResponseEntity<Card>(cardService.changeLimit(name,limit), HttpStatus.OK);
    }
    @PutMapping("/userOp/{name}/status/{status}")
    public ResponseEntity <Card> changeStatus(@PathVariable("name") String name,@PathVariable("status") boolean status)  {

        return new ResponseEntity<Card>(cardService.changeStatus(name,status), HttpStatus.OK);
    }

    @PostMapping("/admin/save")
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

    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('write')")
    public List<Card>getAllCreditCards(){
        var u = SecurityContextHolder.getContext().getAuthentication();

        return cardService.getAllCreditCards();
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
