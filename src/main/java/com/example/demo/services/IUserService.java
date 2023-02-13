package com.example.demo.services;

import com.example.demo.entities.User;

import java.util.List;

public interface IUserService {

    User updateEmail(Long id, String email);
    User updatePhone(Long id, String phone);
    String deleteUser(String email);
    User updateUser(User user, Long id);
    User updateUser1(User user);
    User getUserById(Long id);
    User getUserWithEmail(String email);
    User saveUser(User user);
    List<User> getAllUsers();

}
