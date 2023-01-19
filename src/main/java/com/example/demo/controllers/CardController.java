package com.example.demo.controllers;

import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import com.example.demo.services.CardService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cards")
public class CardController {
    @Autowired
    private CardService cardService;

    //@GetMapping("/users")
    //public List<User>getAllUsers(){
   //  return userService.getAllUsers();
 //}
 @GetMapping("/{name}")
 public ResponseEntity<Integer> getSoldByName(@PathVariable("name") String name){
     return new ResponseEntity<Integer>(cardService.getSoldByName(name), HttpStatus.OK);
 }
    @GetMapping("/")
    public List<Card>getAllCreditCards(){
        return cardService.getAllCreditCards();
   }

}
