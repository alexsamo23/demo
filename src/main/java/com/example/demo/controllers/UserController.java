package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.exceptions.ErrorResponse;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.security.SecurityUser;
import com.example.demo.services.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserController {
    @Autowired
    private  IUserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/admin/delete/{email}")
    @PreAuthorize("hasAuthority('write')")
    public String deleteUser(@PathVariable("email") String email){
        userService.deleteUser((email));
        logger.info("User with email "+ email +" deleted successfully");

        return "redirect:/admin/all";
    }

    @GetMapping("/editUser/{id}")
    public String editForm (@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));

        return "edit_user";
    }

    @PostMapping("/admin/update/{id}")
    @PreAuthorize("hasAuthority('write')")
    public String updateUser(@PathVariable ("id") Long id,@ModelAttribute("user") User user) {
        userService.updateUser(user, id);
        logger.info("User updated successfully");

        return "redirect:/admin/all";
    }

    @GetMapping("/viewOwnDetails")
    public String editOwnForm (Model model) {
        SecurityUser userCustom = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long userId= userCustom.getUser().getId();
        model.addAttribute("user",userService.getUserById(userId));

        return "view_user_details";
    }

    @GetMapping("/editDetails/{id}")
    public String editDetails (@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",userService.getUserById(id));

        return "edit_own_details";
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('write')")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());

        return "viewUsers";
    }


    @PostMapping("/phoneAndEmail/{id}")
    public String updatePhoneAndEmail(@PathVariable("id") Long id,@ModelAttribute("user") User user){
        userService.updateEmail(id,user.getEmail());
        userService.updatePhone(id,user.getPhoneNumber());
        logger.info("User updated successfully");

        return  "redirect:/viewOwnDetails";
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse
    handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        return new ErrorResponse(HttpStatus.NOT_FOUND.value(),
                ex.getMessage());
    }

}
