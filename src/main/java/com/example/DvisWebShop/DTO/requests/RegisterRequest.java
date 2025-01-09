package com.example.DvisWebShop.DTO.requests;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {

    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Integer age;
    private Set<String> roles;
}
