package com.example.DvisWebShop.DTO.requests;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CreateUserRequest {
    private Integer userId;
    private String login;
    private String firstName;
    private String lastName;
    private Integer age;
    private List<Integer> ordersId;
}
