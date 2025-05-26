package com.example.petclinic2;

import lombok.Data;

@Data
public class RegisterRequest {
    private String login;
    private String password;
}
