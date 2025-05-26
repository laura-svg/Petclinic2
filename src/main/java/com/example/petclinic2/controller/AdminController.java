package com.example.petclinic2.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class.getName());

    @GetMapping("/panel")
    public String getAdminPanel() {
        logger.debug("Admin panel opened");
        logger.debug("Deprecated admin feature used");
        return "Admin panel via REST";
    }


}
