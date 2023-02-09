package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.exceptions.ErrorResponse;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.security.SecurityUser;
import com.example.demo.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Controller
public class UserController {

    private final IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin/save")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<User> saveUser(@RequestBody User user){
        
        return new ResponseEntity<>(userService.saveUser(user),HttpStatus.CREATED);
    }

    @GetMapping("/admin/delete/{email}")
    @PreAuthorize("hasAuthority('write')")
    public String deleteUser(@PathVariable("email") String email){

        userService.deleteUser((email));
        logger.info("User with email "+ email +" deleted successfully");

            return "register_success";
    }

    @GetMapping("/editUser/{id}")
    public String editForm (@PathVariable("id") Long id, Model model) {

        model.addAttribute("user", userService.getUserById(id));

        return "edit_user";
    }

    @PostMapping("/admin/update/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String updateUser(@PathVariable ("id") Long id,@ModelAttribute("user") User user, Model model) {

        userService.updateUser(user, id);
        logger.info("User updated successfully");

        return "register_success";
    }

    @GetMapping("/viewOwnDetails")
    public String editOwnForm (Principal principal, Model model) {

        SecurityUser userCustom = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId= userCustom.getUser().getId();
        model.addAttribute("user",userService.getUserById(userId));

        return "view_user_details";
    }

    @GetMapping("/editDetails/{id}")
    public String editEmail (@PathVariable("id") Long id, Model model) {

        model.addAttribute("user",userService.getUserById(id));

        return "edit_own_details";
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('write')")
    public String getAllUsers(Model model){

        model.addAttribute("users", userService.getAllUsers());

        return "viewUsers";
    }


    @PostMapping("/phoneAndEmail/{name}")
    public String updatePhone(@PathVariable("name") String name,@ModelAttribute("user") User user , Model model){

        userService.updateEmail(name,user.getEmail());
        userService.updatePhone(name,user.getPhoneNumber());
        logger.info("User updated successfully");

        return  "register_success";
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
