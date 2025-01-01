package com.example.DvisWebShop.DTO.requests;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CreateUserRequest {
    private Integer userId;
    private String login;
    private String firstName;
    private String lastName;
    private int age;
}
