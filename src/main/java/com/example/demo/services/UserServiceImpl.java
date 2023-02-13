package com.example.demo.services;

import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

//optional
    //stream

    public User updateEmail(Long id, String email) {
        User user = userRepository.findUserById(id);
        user.setEmail(email);
        userRepository.save(user);

        return user;
    }

    public User updatePhone(Long id, String phone){
        User user = userRepository.findUserById(id);
        user.setPhoneNumber(phone);
        userRepository.save(user);

        return user;
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    public User getUserWithEmail(String email){
        return userRepository.findUserByEmail(email);
    }
    public User getUserById(Long id){
        return userRepository.findUserById(id);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public String deleteUser(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email));
        if (user.isPresent()) {
            userRepository.delete(user.get());

            return "User deleted";
        } else {
            throw new ResourceNotFoundException("User", "email", email);
        }
    }

    public User updateUser(User user, Long id){
        User existingUser =userRepository.findUserById(id);
        existingUser.setId(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setEmail(user.getEmail());
        //existingUser.setPassword(user.getPassword());
        userRepository.save(existingUser);

       return existingUser;
    }

    public User updateUser1(User user){

      return  userRepository.save(user);


    }





}
