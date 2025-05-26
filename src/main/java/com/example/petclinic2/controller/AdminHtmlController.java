package com.example.petclinic2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHtmlController {
    private static final Logger logger = LoggerFactory.getLogger(AdminHtmlController.class);

    @GetMapping("/panel")
    public String getAdminPanel() {
        logger.debug("Admin HTML panel accessed");
        return "admin";
    }
}
