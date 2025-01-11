package com.example.DvisWebShop.DTO.responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class JwtResponse {
    @Schema(example = "23token")
    private String token;

    private String type = "Bearer";

    @Schema(example = "1")
    private Integer userId;

    @Schema(example = "admin23")
    private String login;

    @Schema(example = "[ROLE_ADMIN]")
    private List<String> roles;

    public JwtResponse(String token, Integer userId, String login, List<String> roles) {
        this.token = token;
        this.userId = userId;
        this.login = login;
        this.roles = roles;
    }
}
