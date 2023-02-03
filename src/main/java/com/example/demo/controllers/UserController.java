package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.exceptions.ErrorResponse;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.services.IUserService;
import com.example.demo.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
//@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;


    @PostMapping("/admin/save")
    @PreAuthorize("hasAuthority('write')")
    public ResponseEntity<User> saveUser(@RequestBody User user){

        return new ResponseEntity<User>(userService.saveUser(user),HttpStatus.CREATED);
    }

    @GetMapping("/admin/delete/{email}")
    @PreAuthorize("hasAuthority('write')")
    public String deleteUser(@PathVariable("email") String email){
        userService.deleteUser((email));

       // return new ResponseEntity<String>("User deleted succesfully", HttpStatus.OK);


            return "register_success";
    }

    @GetMapping("/editUser/{id}")
    public String editForm (@PathVariable("id") Long id, Model model) {
        model.addAttribute("user",userService.getUserById(id));

        return "edit_user";
    }

    @PostMapping("/admin/update/{id}")
    @PreAuthorize("hasAuthority('write')")
   // public ResponseEntity<User>updateUser(@RequestBody User user,@PathVariable("email") String email){
       // return new ResponseEntity<User>(userService.updateUser(user,email),HttpStatus.OK);
    //public String updateUser(@ModelAttribute("user") User user,Model model,@PathVariable("email") String email){
       // userService.updateUser(user,email);
       // return "register_success";

    public String updateUser(@PathVariable Long id,
                                @ModelAttribute("user") User user,
                                Model model) {

        // get student from database by id
      /*  User existingUser = userService.getUserById(id);
        existingUser.setId(id);
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        //existingUser.setEmail(user.getPhoneNumber());
*/
        // save updated student object
      //  userService.updateUser1(existingUser);
        userService.updateUser(user, id);
        return "register_success";
    }



    @GetMapping("/admin/all")
    @PreAuthorize("hasAuthority('write')")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "viewUsers";
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
