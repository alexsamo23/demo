package com.example.demo.controllers;

import com.example.demo.entities.Card;
import com.example.demo.exceptions.ErrorResponse;
import com.example.demo.exceptions.InvalidWithdrawException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

 @GetMapping("/{name}")
 public ResponseEntity<Integer> checkBalance(@PathVariable("name") String name) throws ResourceNotFoundException {
     return new ResponseEntity<Integer>(cardService.checkBalance(name), HttpStatus.OK);
 }

    @PutMapping("/{name}/deposit/{amount}")
    public ResponseEntity <Card> deposit(@PathVariable("name") String name,@PathVariable("amount") int amount) {
      return new ResponseEntity<Card>(cardService.deposit(name,amount), HttpStatus.OK);
    }

    @PutMapping("/{name}/withdraw/{amount}")
    public ResponseEntity <Card> withdraw(@PathVariable("name") String name,@PathVariable("amount") int amount) throws InvalidWithdrawException {
        return new ResponseEntity<Card>(cardService.withdraw(name, amount), HttpStatus.OK);
    }


    @PutMapping("/{name}/limit/{limit}")
    public ResponseEntity <Card> changeLimit(@PathVariable("name") String name,@PathVariable("limit") int limit)  {
        return new ResponseEntity<Card>(cardService.changeLimit(name,limit), HttpStatus.OK);
    }
    @PutMapping("/{name}/status/{status}")
    public ResponseEntity <Card> changeStatus(@PathVariable("name") String name,@PathVariable("status") boolean status)  {
        return new ResponseEntity<Card>(cardService.changeStatus(name,status), HttpStatus.OK);
    }
    @GetMapping("/")
    public List<Card>getAllCreditCards(){
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
