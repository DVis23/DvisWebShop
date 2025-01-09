package com.example.DvisWebShop.DTO.responses;

import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Integer userId;
    private String login;
    private List<String> roles;

    public JwtResponse(String token, Integer userId, String login, List<String> roles) {
        this.token = token;
        this.userId = userId;
        this.login = login;
        this.roles = roles;
    }
}
