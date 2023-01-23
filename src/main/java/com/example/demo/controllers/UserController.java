package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.exceptions.ErrorResponse;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping()
    public ResponseEntity<User> saveUser(@RequestBody User user){
        return new ResponseEntity<User>(userService.saveUser(user),HttpStatus.CREATED);
    }
    @DeleteMapping("/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email){
        userService.deleteUser((email));
        return new ResponseEntity<String>("User deleted succesfully", HttpStatus.OK);
    }
    @GetMapping("/")
    public List<User> getAllUsers(){
        return userService.getAllUsers();

    }

    @PutMapping("/{name}/email/{email}")
    public ResponseEntity<User> updateEmail(@PathVariable("name") String name, @PathVariable("email") String email){
        return new ResponseEntity<User>(userService.updateEmail(name,email), HttpStatus.OK);
    }

    @PutMapping("/{name}/phone/{phone}")
    public ResponseEntity<User> updatePhone(@PathVariable("name") String name, @PathVariable("phone") String phone){
        return new ResponseEntity<User>(userService.updatePhone(name,phone),HttpStatus.OK);
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
