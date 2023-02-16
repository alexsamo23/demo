package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.exceptions.ResourceNotFoundException;
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
        //given
        User user1 = new User();
        user1.setId(1);
        user1.setEmail("alex");
        //when
        Mockito.when(userRepository.findUserById(1L)).thenReturn(user1);
        //then
        User user = userService.updateEmail(user1.getId(),"alexsamo");
        assertEquals("alexsamo",user.getEmail());
    }

    @Test
    void verifyIfPhoneNumberIsUpdated() {
        //given
        User user1 = new User();
        user1.setId(1);
        user1.setPhoneNumber("0745");
        //when
        Mockito.when(userRepository.findUserById(1L)).thenReturn(user1);
        //then
        User user = userService.updatePhone(user1.getId(),"0755");
        assertEquals("0755",user.getPhoneNumber());
    }

    @Test
    void verifyIfAllUsersAreReturned() {
        //given
        User user1 = new User();
        User user2 = new User();
        List<User> input = new ArrayList<User>();
        input.add(user1);
        input.add(user2);
        //when
        Mockito.when(userRepository.findAll()).thenReturn(input);
        //then
        List<User> result= userService.getAllUsers();
        assertEquals(2,result.size());
    }

    @Test
    void verifyIfUserIsSaved() {
        //given
        User user1 = new User();
        //when
        Mockito.when(userRepository.save(user1)).thenReturn(user1);
        //then
        User user = userService.saveUser(user1);
        assertEquals(user1,user);
    }

    @Test
    void verifyIfUserIsDeleted() {
        //given
        User user1 = new User();
        user1.setEmail("alex");
        //when
        Mockito.when(userRepository.findUserByEmail("alex")).thenReturn(user1);
        //then
        String  user = userService.deleteUser("alex");
        assertEquals("User deleted",user);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            userService.deleteUser("email");
        });
        String expectedMessage = "User not found with email : 'email'";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void verifyIfUserIsUpdated() {
        //given
        User user1 = new User();
        user1.setId(1);

        User user2 = new User();
        user2.setId(2);
        user2.setFirstName("user2");
        user2.setLastName("1234");
        user2.setEmail("email2");
        user2.setPassword("1234");
        user2.setPhoneNumber("0745");
        //when
        Mockito.when(userRepository.findUserById(1L)).thenReturn(user1);
        //then
        User user = userService.updateUser(user2,user1.getId());
        assertEquals("email2",user.getEmail());
    }
}