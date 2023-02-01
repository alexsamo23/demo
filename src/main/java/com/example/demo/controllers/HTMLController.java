package com.example.demo.controllers;

import com.example.demo.entities.Authority;
import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HTMLController {

    @Autowired
    private UserRepository userRepository;

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
        // List<GrantedAuthority> authorities = new ArrayList<>();
 //Set<SimpleGrantedAuthority> set = new HashSet<SimpleGrantedAuthority>();
 //SimpleGrantedAuthority authority= new SimpleGrantedAuthority("read");
//set.add(authority);
        Authority authority= new Authority();
        authority.setName("read");
        Set<Authority> authorities = new HashSet<>();
        authorities.add(authority);
       user.setAuthorities(authorities);
       userRepository.save(user);

        return "register_success";
    }

}
