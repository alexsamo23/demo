package com.example.demo.controllers;

import com.example.demo.entities.Authority;
import com.example.demo.entities.User;
import com.example.demo.repositories.AuthorityRepository;
import com.example.demo.services.IUserService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Controller
public class RegistrationController {

    private static Logger logger = Logger.getLogger(RegistrationController.class.getName());
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

        @GetMapping("/login")
        public String viewLoginPage() {
            return "login";

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

        if ((userService.getUserWithEmail(user.getEmail())) == null) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            Authority authority = authorityRepository.findAuthorityByName("read");
            Set<Authority> authorities = new HashSet<>();
            authorities.add(authority);
            user.setAuthorities(authorities);
            userService.saveUser(user);

            logger.info("User successfully created");
            return "register_success";
        }
        else {
            logger.warning("Exist user with email "+ user.getEmail());
            return "signup_form";
        }
    }

}
