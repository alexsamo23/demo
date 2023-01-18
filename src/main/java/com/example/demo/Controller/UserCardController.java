package com.example.demo.Controller;

import com.example.demo.Entities.Card;
import com.example.demo.UserCardServices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserCardController {
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public List<Card>getAllCreditCards(){
        return userService.getAllCreditCards();
    }

}
