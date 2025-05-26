package com.example.petclinic2.controller;

import com.example.petclinic2.RegisterRequest;
import com.example.petclinic2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("admin/home")
    public String adminHome() {
        return "admin";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(Authentication auth) {
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
        {
            return "redirect:/admin/home";
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")))
        {    return "redirect:/user/home";
        }
        return "redirect:/access-denied";
    }

        @GetMapping("/logout")
        public String logoutRedirect() {
            return "redirect:/login?logout";
        }

    private final UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new RegisterRequest());
        return "register"; // register.html
    }


    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") RegisterRequest request, Model model) {
        try {
            userService.registerUser(request.getLogin(), request.getPassword());
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
