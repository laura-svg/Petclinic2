package com.example.petclinic2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.security.Principal;

@Controller
public class UserViewController {
  //  private static final Logger logger = LoggerFactory.getLogger(UserViewController.class);
    @GetMapping("/user/home")
    public String userHomePage(Model model, Principal principal) {
        String username = (principal != null) ? principal.getName() : "Guest";
       // logger.info(">>> USER HOME ACCESSED BY: {}", username);
        model.addAttribute("username", username);
        return "user"; // Це має бути HTML шаблон user.html
    }

    @GetMapping("/user/profile")
    public String userProfilePage(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "user"; // або окремий шаблон profile.html
    }
}
