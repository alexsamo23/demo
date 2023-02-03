package com.example.demo.services;

import com.example.demo.entities.Card;
import com.example.demo.entities.User;
import com.example.demo.repositories.CardRepository;
import com.example.demo.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void verifyIfEmailIsUpdated() {
        User user1 = new User();
        user1.setFirstName("user");
        user1.setEmail("alex");

        Mockito.when(userRepository.findUserByFirstName("user")).thenReturn(user1);

        User user = userService.updateEmail(user1.getFirstName(),"alexsamo");

        assertEquals("alexsamo",user.getEmail());
    }

    @Test
    void verifyIfPhoneNumberIsUpdated() {
        User user1 = new User();
        user1.setFirstName("user");
        user1.setPhoneNumber("0745");

        Mockito.when(userRepository.findUserByFirstName("user")).thenReturn(user1);

        User user = userService.updatePhone(user1.getFirstName(),"0755");

        assertEquals("0755",user.getPhoneNumber());
    }

    @Test
    void verifyIfAllUsersAreReturned() {
        User user1 = new User();
        User user2 = new User();
        List<User> input = new ArrayList<User>();
        input.add(user1);
        input.add(user2);


        Mockito.when(userRepository.findAll()).thenReturn(input);

        List<User> result= userService.getAllUsers();

        assertEquals(2,result.size());
    }

    @Test
    void verifyIfUserIsSaved() {
        User user1 = new User();

        Mockito.when(userRepository.save(user1)).thenReturn(user1);

        User user = userService.saveUser(user1);
        assertEquals(user1,user);
    }

    @Test
    void verifyIfUserIsDeleted() {
        User user1 = new User();
        user1.setEmail("alex");

        Mockito.when(userRepository.findUserByEmail("alex")).thenReturn(user1);

        String  user = userService.deleteUser("alex");

        assertEquals("User deleted",user);
    }

    @Test
    void verifyIfUserIsUpdated() {
        User user1 = new User();
        user1.setEmail("email");

        User user2 = new User();
        user2.setId(1);
        user2.setFirstName("user2");
        user2.setLastName("1234");
        user2.setEmail("email2");
        user2.setPassword("1234");
        user2.setPhoneNumber("0745");


        Mockito.when(userRepository.findUserByEmail("email")).thenReturn(user1);

        User user = userService.updateUser(user2,"email");

        assertEquals("email2",user.getEmail());
    }
}