package com.example.DvisWebShop.DTO.responses;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserResponse {
    private Integer userId;
    private String login;
    private String firstName;
    private String lastName;
    private int age;
    private List<OrderResponse> orders;
}