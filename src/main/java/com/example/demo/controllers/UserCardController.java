package com.example.demo.controllers;

import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("/cards")
public class UserCardController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User>getAllUsers(){
     return userService.getAllUsers();
 }

    @GetMapping("/cards")
    public List<Card>getAllCreditCards(){
        return userService.getAllCreditCards();
    }

}
