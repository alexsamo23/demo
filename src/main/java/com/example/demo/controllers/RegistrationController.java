package com.example.demo.controllers;

import com.example.demo.entities.Authority;
import com.example.demo.entities.User;
import com.example.demo.repositories.AuthorityRepository;
import com.example.demo.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {

    @Autowired
    private IUserService userService;
    @Autowired
    private AuthorityRepository authorityRepository;

        @GetMapping("/login")
        public String viewLoginPage() {
            return "login.html";

        }
        @GetMapping("/index")
        public String viewIndexPage() {
            return "index.html";

        }
        @GetMapping("/")
        public String viewHomePage() {
            return "home.html";

        }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup_form.html";
    }
    @PostMapping("/process_register")
    public String processRegister(User user) {
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //String encodedPassword = passwordEncoder.encode(user.getPassword());
       // user.setPassword(encodedPassword);
        Authority authority= authorityRepository.findAuthorityByName("read");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
        user.setAuthorities(authorities);
        userService.saveUser(user);

        return "register_success";
    }

}