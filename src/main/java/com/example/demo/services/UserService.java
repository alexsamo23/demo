package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.repositories.CardRepository;
import com.example.demo.entities.Card;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

//optional
    //stream

    public User updateEmail(String name, String email) {
        User user = userRepository.findUserByFirstName(name);
        user.setEmail(email);
        userRepository.save(user);
        return user;

    }

    public User updatePhone(String name, String phone){
        User user = userRepository.findUserByFirstName(name);
        user.setPhoneNumber(phone);
        userRepository.save(user);
        return user;
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }



}
